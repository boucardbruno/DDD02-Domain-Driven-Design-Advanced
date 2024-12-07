package com.octo.technology.ExternalDependencies;

import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumDto;
import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumLayoutRepository;
import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.SeatDto;
import com.octo.technology.ExternalDependencies.reservationsprovider.ReservationsProvider;
import com.octo.technology.ExternalDependencies.reservationsprovider.ReservedSeatsDto;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class ExternalDependenciesTest {

    @Test
    public void allow_us_to_retrieve_reserved_seats_for_a_given_ShowId() throws IOException, URISyntaxException {
        ReservationsProvider seatsRepository = new ReservationsProvider();
        ReservedSeatsDto reservedSeatsDto = seatsRepository.getReservedSeats("1");

        org.assertj.core.api.Assertions.assertThat(reservedSeatsDto.reservedSeats()).hasSize(19);
    }

    @Test
    public void allow_us_to_retrieve_AuditoriumLayout_for_a_given_ShowId() throws IOException, URISyntaxException {

        AuditoriumLayoutRepository eventRepository = new AuditoriumLayoutRepository();
        AuditoriumDto theaterDto = eventRepository.GetAuditoriumLayoutFor("2");

        org.assertj.core.api.Assertions.assertThat(theaterDto.rows()).hasSize(6);
        org.assertj.core.api.Assertions.assertThat(theaterDto.corridors()).hasSize(2);
        SeatDto firstSeatOfFirstRow = theaterDto.rows().get("A").get(0);
        org.assertj.core.api.Assertions.assertThat(firstSeatOfFirstRow.category()).isEqualTo(2);

    }

}