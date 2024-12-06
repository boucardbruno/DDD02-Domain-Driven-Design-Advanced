package com.octo.technology.SeatsSuggestionsAcceptanceTests;

import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumDto;
import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumLayoutRepository;
import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.SeatDto;
import com.octo.technology.ExternalDependencies.reservationsprovider.ReservationsProvider;
import com.google.common.collect.ImmutableList;
import com.octo.technology.ExternalDependencies.reservationsprovider.ReservedSeatsDto;

import java.util.HashMap;
import java.util.Map;

public class AuditoriumSeatingAdapter {

    private final ReservationsProvider reservedSeatsRepository;
    private final AuditoriumLayoutRepository auditoriumLayoutRepository;


    public AuditoriumSeatingAdapter(AuditoriumLayoutRepository auditoriumLayoutRepository, ReservationsProvider reservationsProvider) {
        this.auditoriumLayoutRepository = auditoriumLayoutRepository;
        this.reservedSeatsRepository = reservationsProvider;
    }

    public AuditoriumSeating getAuditoriumSeating(String showId) {
        return adapt(auditoriumLayoutRepository.getAuditoriumLayoutFor(showId),
                reservedSeatsRepository.getReservedSeats(showId));

    }

    private AuditoriumSeating adapt(AuditoriumDto auditoriumDto, ReservedSeatsDto reservedSeatsDto) {

        Map<String, Row> rows = new HashMap<>();

        for (Map.Entry<String, ImmutableList<SeatDto>> entry : auditoriumDto.rows().entrySet()) {
            entry.getValue().forEach(seatDto -> {
                String rowName = extractRowName(seatDto.name());
                int number = extractNumber(seatDto.name());
                PricingCategory pricingCategory = convertCategory(seatDto.category());

                boolean isReserved = reservedSeatsDto.reservedSeats().contains(seatDto.name());

                if (!rows.containsKey(rowName)) {
                    rows.put(rowName, new Row());
                }

                rows.get(rowName).seats().add(new Seat(rowName, number, pricingCategory,
                        isReserved ? SeatAvailability.Reserved : SeatAvailability.Available));
            });
        }

        return new AuditoriumSeating(rows);
    }

    private static PricingCategory convertCategory(int seatDtoCategory) {
        return PricingCategory.valueOf(seatDtoCategory);
    }

    private static int extractNumber(String name) {
        return Integer.parseUnsignedInt(name.substring(1));
    }

    private static String extractRowName(String name) {
        return Character.toString(name.charAt(0));
    }
}