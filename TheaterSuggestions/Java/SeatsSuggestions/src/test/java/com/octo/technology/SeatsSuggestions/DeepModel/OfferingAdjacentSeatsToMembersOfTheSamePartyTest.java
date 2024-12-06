package com.octo.technology.SeatsSuggestions.DeepModel;


import com.octo.technology.SeatsSuggestions.*;
import com.octo.technology.SeatsSuggestions.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;


public class OfferingAdjacentSeatsToMembersOfTheSamePartyTest {

    @Test
    public void Offer_groups_of_adjacent_seats_when_row_contains_some_reserved_seats()
    {
        var partySize = 3;
        var a1 = new Seat("A", 1, PricingCategory.Second, SeatAvailability.Available);
        var a2 = new Seat("A", 2, PricingCategory.Second, SeatAvailability.Available);
        var a3 = new Seat("A", 3, PricingCategory.First, SeatAvailability.Available);
        var a4 = new Seat("A", 4, PricingCategory.First, SeatAvailability.Reserved);
        var a5 = new Seat("A", 5, PricingCategory.First, SeatAvailability.Available);
        var a6 = new Seat("A", 6, PricingCategory.First, SeatAvailability.Available);
        var a7 = new Seat("A", 7, PricingCategory.First, SeatAvailability.Available);
        var a8 = new Seat("A", 8, PricingCategory.First, SeatAvailability.Reserved);
        var a9 = new Seat("A", 9, PricingCategory.Second, SeatAvailability.Available);
        var a10 = new Seat("A", 10, PricingCategory.Second, SeatAvailability.Available);

        var row = new Row("A", Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10));
        var seatsWithDistance =
                OfferSeatingPlacesNearerTheMiddleOfTheRow
                        .buildSeatingPlaceCloserTheMiddleOfTheRow(row,
                                new SuggestionRequest(partySize, PricingCategory.Mixed))
                        .stream().limit(partySize).collect(Collectors.toList());

        assertThat(OfferSeatingPlacesAdjacentFromTheMiddleOfTheRow
                        .buildAdjacentSeats(seatsWithDistance,
                               partySize))
                .containsExactly(a5, a6, a7);
    }


     @Test
    public void Offer_adjacent_seats_nearer_the_middle_of_the_row_when_the_middle_is_not_reserved()
    {
        var partySize = 3;
        var a1 = new Seat("A", 1, PricingCategory.Second, SeatAvailability.Available);
        var a2 = new Seat("A", 2, PricingCategory.Second, SeatAvailability.Available);
        var a3 = new Seat("A", 3, PricingCategory.First, SeatAvailability.Available);
        var a4 = new Seat("A", 4, PricingCategory.First, SeatAvailability.Reserved);
        var a5 = new Seat("A", 5, PricingCategory.First, SeatAvailability.Available);
        var a6 = new Seat("A", 6, PricingCategory.First, SeatAvailability.Available);
        var a7 = new Seat("A", 7, PricingCategory.First, SeatAvailability.Available);
        var a8 = new Seat("A", 8, PricingCategory.First, SeatAvailability.Reserved);
        var a9 = new Seat("A", 9, PricingCategory.Second, SeatAvailability.Available);
        var a10 = new Seat("A", 10, PricingCategory.Second, SeatAvailability.Available);

        var row = new Row("A", Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10));

        var seatsWithDistance =
                OfferSeatingPlacesNearerTheMiddleOfTheRow
                        .buildSeatingPlaceCloserTheMiddleOfTheRow(row,
                                new SuggestionRequest(partySize, PricingCategory.Mixed))
                        .stream().limit(partySize).collect(Collectors.toList());

        assertThat(OfferSeatingPlacesAdjacentFromTheMiddleOfTheRow
                        .buildAdjacentSeats(seatsWithDistance,
                                partySize))
                .containsExactly(a5, a6, a7);
    }
}

