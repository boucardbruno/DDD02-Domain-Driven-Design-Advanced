package com.octo.technology.SeatsSuggestions.DeepModel;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.octo.technology.SeatsSuggestions.*;
import com.octo.technology.SeatsSuggestions.*;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class OfferingSeatsNearerMiddleOfTheRowTest {
    @Test
    public void Offer_seats_from_the_middle_of_the_row_when_the_row_size_is_even_and_party_size_is_greater_than_one()
    {
        var partySize = 2;

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

        var row = new Row("A", Arrays.asList( a1, a2, a3, a4, a5, a6, a7, a8, a9, a10));

        var seatsWithDistance = OfferSeatingPlacesNearerTheMiddleOfTheRow
                .buildSeatingPlaceCloserTheMiddleOfTheRow(row, new SuggestionRequest(partySize, PricingCategory.Mixed))
                .stream().limit(partySize);

        assertThat(seatsWithDistance.map(SeatWithDistance::seat).collect(Collectors.toList()))
                .containsExactly(a5, a6);
    }

    @Test
    public void Offer_seats_from_the_middle_of_the_row_when_the_row_size_is_and_and_party_size_is_greater_than_one()
    {
        var partySize = 5;

        var a1 = new Seat("A", 1, PricingCategory.Second, SeatAvailability.Available);
        var a2 = new Seat("A", 2, PricingCategory.Second, SeatAvailability.Available);
        var a3 = new Seat("A", 3, PricingCategory.First, SeatAvailability.Available);
        var a4 = new Seat("A", 4, PricingCategory.First, SeatAvailability.Reserved);
        var a5 = new Seat("A", 5, PricingCategory.First, SeatAvailability.Available);
        var a6 = new Seat("A", 6, PricingCategory.First, SeatAvailability.Available);
        var a7 = new Seat("A", 7, PricingCategory.First, SeatAvailability.Available);
        var a8 = new Seat("A", 8, PricingCategory.First, SeatAvailability.Reserved);
        var a9 = new Seat("A", 9, PricingCategory.Second, SeatAvailability.Available);

        var row = new Row("A", Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9));
        var seatsWithDistance = OfferSeatingPlacesNearerTheMiddleOfTheRow
                .buildSeatingPlaceCloserTheMiddleOfTheRow(row, new SuggestionRequest(partySize, PricingCategory.Mixed))
                .stream().limit(partySize);

        assertThat(seatsWithDistance.map(SeatWithDistance::seat).sorted((s1, s2) -> Integer.compare(s1.number(), s2.number())).collect(Collectors.toList()))
                .containsExactly(a2, a3, a5, a6, a7);
    }
}

