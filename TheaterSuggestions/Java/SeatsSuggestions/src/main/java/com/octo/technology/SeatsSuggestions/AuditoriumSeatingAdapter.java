package com.octo.technology.SeatsSuggestions;

import com.octo.technology.ExternalDependencies.IProvideAuditoriumLayouts;
import com.octo.technology.ExternalDependencies.IProvideCurrentReservations;
import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumDto;
import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.SeatDto;
import com.octo.technology.ExternalDependencies.reservationsprovider.ReservedSeatsDto;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditoriumSeatingAdapter {

    private final IProvideCurrentReservations reservedSeatsRepository;
    private final IProvideAuditoriumLayouts auditoriumLayoutRepository;


    public AuditoriumSeatingAdapter(IProvideAuditoriumLayouts auditoriumLayoutRepository, IProvideCurrentReservations reservationsProvider) {
        this.auditoriumLayoutRepository = auditoriumLayoutRepository;
        this.reservedSeatsRepository = reservationsProvider;
    }

    private static PricingCategory convertCategory(int seatDtoCategory) {
        return PricingCategory.valueOf(seatDtoCategory);
    }

    private static int extractNumber(String name) {
        return Integer.parseUnsignedInt(name.substring(1));
    }

    public AuditoriumSeating getAuditoriumSeating(String showId) {
        return adapt(auditoriumLayoutRepository.getAuditoriumSeatingFor(showId),
                reservedSeatsRepository.getReservedSeats(showId));

    }

    private AuditoriumSeating adapt(AuditoriumDto auditoriumDto, ReservedSeatsDto reservedSeatsDto) {

        Map<String, Row> rows = new HashMap<>();

        for (Map.Entry<String, ImmutableList<SeatDto>> rowDto : auditoriumDto.rows.entrySet()) {
            List<Seat> seats = new ArrayList<>();

            rowDto.getValue().forEach(seatDto -> {
                String rowName = rowDto.getKey();
                int number = extractNumber(seatDto.name);
                PricingCategory pricingCategory = convertCategory(seatDto.category);

                boolean isReserved = reservedSeatsDto.reservedSeats.contains(seatDto.name);

                seats.add(new Seat(rowName, number, pricingCategory, isReserved ? SeatAvailability.Reserved : SeatAvailability.Available));
            });

            rows.put(rowDto.getKey(), new Row(rowDto.getKey(), seats));
        }

        return new AuditoriumSeating(rows);
    }
}