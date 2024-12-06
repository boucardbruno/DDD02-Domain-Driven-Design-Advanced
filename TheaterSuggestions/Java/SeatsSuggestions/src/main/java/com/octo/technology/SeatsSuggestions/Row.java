package com.octo.technology.SeatsSuggestions;
import com.octo.technology.SeatsSuggestions.DeepModel.OfferSeatingPlacesNearerTheMiddleOfTheRow;
import com.octo.technology.SeatsSuggestions.DeepModel.SeatWithDistance;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Row {
    private final String name;
    private final List<Seat> seats;

    public
    Row(String name, List<Seat> seats) {

        this.name = name;
        this.seats = seats
                .stream()
                .map(s -> new Seat(
                        s.rowName(),
                        s.number(),
                        s.pricingCategory(),
                        s.seatAvailability()))
                        .collect(Collectors.toList());
    }

    public List<Seat>
    seats() {
        return seats;
    }

    public Row
    addSeat(Seat seat) {

        ArrayList<Seat> newSeats = new ArrayList<>(this.seats);
        newSeats.add(seat);
        return new Row(name, newSeats);
    }

    public SeatingOptionSuggested
    suggestSeatingOption(SuggestionRequest suggestionRequest) {

        SeatingOptionSuggested seatingOptionSuggested = new SeatingOptionSuggested(suggestionRequest);

        for (Seat seat : offerAdjacentSeatsNearerTheMiddleOfTheRow(suggestionRequest)) {
            seatingOptionSuggested.addSeat(seat);

            if (seatingOptionSuggested.matchExpectation()) {
                return seatingOptionSuggested;
            }
        }

        return new SeatingOptionNotAvailable(suggestionRequest);
    }

    public List<Seat>
    offerAdjacentSeatsNearerTheMiddleOfTheRow(SuggestionRequest suggestionRequest)
    {
        // 1. offer seats from the middle of the row
        var seatWithTheDistanceFromTheMiddleOfTheRows =
                OfferSeatingPlacesNearerTheMiddleOfTheRow.buildSeatingPlaceCloserTheMiddleOfTheRow(this, suggestionRequest);

        return seatWithTheDistanceFromTheMiddleOfTheRows.stream().map(SeatWithDistance::seat).collect(Collectors.toList());
    }


    public Row
    allocate(Seat seat) {
        List<Seat> newVersionOfSeats = new ArrayList<>();

        seats.forEach(currentSeat -> {
            if (currentSeat.sameSeatLocation(seat)) {
                newVersionOfSeats.add(new Seat(seat.rowName(), seat.number(), seat.pricingCategory(),
                        SeatAvailability.Allocated));
            } else {
                newVersionOfSeats.add(currentSeat);
            }
        });

        return new Row(seat.rowName(), newVersionOfSeats);
    }

    public boolean
    rowSizeIsEven() {

        return seats().size() % 2 == 0;
    }

    public int
    theMiddleOfRow() {

        return rowSizeIsEven() ? seats().size() / 2 : Math.abs(seats().size() / 2) + 1;
    }
}