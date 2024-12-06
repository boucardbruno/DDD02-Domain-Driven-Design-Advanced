package com.octo.technology.ExternalDependencies;

import com.octo.technology.ExternalDependencies.reservationsprovider.ReservedSeatsDto;

public interface IProvideCurrentReservations {

    ReservedSeatsDto getReservedSeats(String showId);
}
