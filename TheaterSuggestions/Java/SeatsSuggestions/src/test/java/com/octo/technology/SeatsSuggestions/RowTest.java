package com.octo.technology.SeatsSuggestions;

import com.octo.technology.SeatsSuggestions.DeepModel.OfferSeatingPlacesNearerTheMiddleOfTheRow;
import com.octo.technology.SeatsSuggestions.DeepModel.SeatWithDistance;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

public class RowTest {

    @Test
    public void be_a_Value_Type() {
        Seat a1 = new Seat("A", 1, PricingCategory.Second, SeatAvailability.Available);
        Seat a2 = new Seat("A", 2, PricingCategory.Second, SeatAvailability.Available);

        // Two different instances with same values should be equals
        Row rowFirstInstance = new Row("A", Arrays.asList(a1, a2));
        Row rowSecondInstance = new Row("A", Arrays.asList(a1, a2));
        assertThat(rowSecondInstance).isEqualTo(rowFirstInstance);

        // Should not mutate existing instance
        Seat a3 = new Seat("A", 2, PricingCategory.Second, SeatAvailability.Available);
        Row newRowWithNewSeatAdded = rowSecondInstance.addSeat(a3);
        assertThat(newRowWithNewSeatAdded).isNotEqualTo(rowFirstInstance);
    }


    @Test
    public void offer_seats_from_the_middle_of_the_row_when_the_row_size_is_even_and_party_size_is_greater_than_one() {
        int partySize = 2;

        Seat a1 = new Seat("A", 1, PricingCategory.Second, SeatAvailability.Available);
        Seat a2 = new Seat("A", 2, PricingCategory.Second, SeatAvailability.Available);
        Seat a3 = new Seat("A", 3, PricingCategory.First, SeatAvailability.Available);
        Seat a4 = new Seat("A", 4, PricingCategory.First, SeatAvailability.Reserved);
        Seat a5 = new Seat("A", 5, PricingCategory.First, SeatAvailability.Available);
        Seat a6 = new Seat("A", 6, PricingCategory.First, SeatAvailability.Available);
        Seat a7 = new Seat("A", 7, PricingCategory.First, SeatAvailability.Available);
        Seat a8 = new Seat("A", 8, PricingCategory.First, SeatAvailability.Reserved);
        Seat a9 = new Seat("A", 9, PricingCategory.Second, SeatAvailability.Available);
        Seat a10 = new Seat("A", 10, PricingCategory.Second, SeatAvailability.Available);

        Row row = new Row("A", new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)));

        List<SeatWithDistance> seatsWithDistance = new ArrayList<>(OfferSeatingPlacesNearerTheMiddleOfTheRow
                .buildSeatingPlaceCloserTheMiddleOfTheRow(row, new SuggestionRequest(partySize, PricingCategory.Mixed)));

        List<Seat> seats = seatsWithDistance.stream().map(SeatWithDistance::seat).limit(partySize).collect(Collectors.toList());

        assertThat(seats).containsExactly(a5, a6);
    }


    @Test
    public void offer_seats_from_the_middle_of_the_row_when_with_the_row_size_is_odd_and_party_size_is_greater_than_one() {
        int partySize = 5;

        Seat a1 = new Seat("A", 1, PricingCategory.Second, SeatAvailability.Available);
        Seat a2 = new Seat("A", 2, PricingCategory.Second, SeatAvailability.Available);
        Seat a3 = new Seat("A", 3, PricingCategory.First, SeatAvailability.Available);
        Seat a4 = new Seat("A", 4, PricingCategory.First, SeatAvailability.Reserved);
        Seat a5 = new Seat("A", 5, PricingCategory.First, SeatAvailability.Available);
        Seat a6 = new Seat("A", 6, PricingCategory.First, SeatAvailability.Available);
        Seat a7 = new Seat("A", 7, PricingCategory.First, SeatAvailability.Available);
        Seat a8 = new Seat("A", 8, PricingCategory.First, SeatAvailability.Reserved);
        Seat a9 = new Seat("A", 9, PricingCategory.Second, SeatAvailability.Available);

        Row row = new Row("A", new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9)));

        List<SeatWithDistance> seatsWithDistance = OfferSeatingPlacesNearerTheMiddleOfTheRow
                .buildSeatingPlaceCloserTheMiddleOfTheRow(row, new SuggestionRequest(partySize, PricingCategory.Mixed)).stream().limit(partySize).collect(Collectors.toList());

        List<Seat> seats = seatsWithDistance.stream().map(SeatWithDistance::seat).sorted(Comparator.comparingInt(Seat::number)).collect(Collectors.toList());

        assertThat(seats).containsExactly(a2, a3, a5, a6, a7);
    }

    @Test
    public void Offer_adjacent_seats_nearer_the_middle_of_the_row_when_the_middle_is_not_reserved()
    {
        int partySize = 3;

        Seat a1 = new Seat("A", 1, PricingCategory.Second, SeatAvailability.Available);
        Seat a2 = new Seat("A", 2, PricingCategory.Second, SeatAvailability.Available);
        Seat a3 = new Seat("A", 3, PricingCategory.First, SeatAvailability.Available);
        Seat a4 = new Seat("A", 4, PricingCategory.First, SeatAvailability.Reserved);
        Seat a5 = new Seat("A", 5, PricingCategory.First, SeatAvailability.Available);
        Seat a6 = new Seat("A", 6, PricingCategory.First, SeatAvailability.Available);
        Seat a7 = new Seat("A", 7, PricingCategory.First, SeatAvailability.Available);
        Seat a8 = new Seat("A", 8, PricingCategory.First, SeatAvailability.Reserved);
        Seat a9 = new Seat("A", 9, PricingCategory.Second, SeatAvailability.Available);
        Seat a10 = new Seat("A", 10, PricingCategory.Second, SeatAvailability.Available);

        Row row = new Row("A",  new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10 )));

        List<SeatWithDistance> seatsWithDistance =
                OfferSeatingPlacesNearerTheMiddleOfTheRow
                        .buildSeatingPlaceCloserTheMiddleOfTheRow(row,
                        new SuggestionRequest(5, PricingCategory.Mixed));

        List<Seat> seats = OfferAdjacentSeats(seatsWithDistance, partySize);

        assertThat(seats).containsExactly(a5, a6, a7);
    }

    private List<Seat> OfferAdjacentSeats(List<SeatWithDistance> seatsWithDistance, int partySize) {
        // Implement your prototype here
        return new ArrayList<>();
    }
}