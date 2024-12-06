package com.octo.technology.SeatReservationsApi.controllers;


import com.octo.technology.ExternalDependencies.IProvideCurrentReservations;
import com.octo.technology.ExternalDependencies.reservationsprovider.ReservedSeatsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/data_for_reservation_seats")
public class ReservationSeatsController {

    private final IProvideCurrentReservations provideCurrentReservations;

    public ReservationSeatsController(IProvideCurrentReservations provideCurrentReservations) {
        this.provideCurrentReservations = provideCurrentReservations;
    }

    // GET api/data_for_reservation_seats/5
    @GetMapping(value = "/{showId}", produces = "application/json")
    public ReservedSeatsDto get(@PathVariable String showId) {
        return provideCurrentReservations.getReservedSeats(showId);
    }
}
