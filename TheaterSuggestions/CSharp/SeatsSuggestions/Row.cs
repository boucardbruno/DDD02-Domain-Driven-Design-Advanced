﻿using System.Collections.Generic;
using System.Linq;
using SeatsSuggestions.DeepModel;
using Value;

namespace SeatsSuggestions;

public class Row(string name, IReadOnlyList<Seat> seats) : ValueType<Row>
{
    private string Name { get; } = name;
    public IReadOnlyList<Seat> Seats { get; } = seats;

    public Row AddSeat(Seat seat)
    {
        return new Row(Name, new List<Seat>(Seats) { seat });
    }

    public SeatingOptionSuggested SuggestSeatingOption(SuggestionRequest suggestionRequest)
    {
        var seatingOptionSuggested = new SeatingOptionSuggested(suggestionRequest);

        foreach (var seat in OfferAdjacentSeatsNearerTheMiddleOfTheRow(suggestionRequest))
        {
            seatingOptionSuggested.AddSeat(seat);

            if (seatingOptionSuggested.MatchExpectation()) return seatingOptionSuggested;
        }

        return new SeatingOptionNotAvailable(suggestionRequest);
    }

    public IEnumerable<Seat> OfferAdjacentSeatsNearerTheMiddleOfTheRow(SuggestionRequest suggestionRequest)
    {
        // 1. offer seats from the middle of the row
        var seatsWithDistanceFromMiddleOfTheRow =
            OfferSeatingPlacesNearerTheMiddleOfTheRow.BuildSeatingPlaceCloserTheMiddleOfTheRow(this, suggestionRequest);

        if (DoNotLookForAdjacentSeatsWhenThePartyContainsOnlyOnePerson(suggestionRequest))
            return seatsWithDistanceFromMiddleOfTheRow.Select(seatWithTheDistanceFromTheMiddleOfTheRow =>
                seatWithTheDistanceFromTheMiddleOfTheRow.Seat);

        // 2. based on seats with distance from the middle of row,
        //    we offer the best group (close to the middle) of adjacent seats
        return OfferSeatingPlacesAdjacentFromTheMiddleOfTheRow
            .BuildAdjacentSeats(seatsWithDistanceFromMiddleOfTheRow, suggestionRequest);
    }

    private static bool DoNotLookForAdjacentSeatsWhenThePartyContainsOnlyOnePerson(
        SuggestionRequest suggestionRequest)
    {
        return suggestionRequest.PartyRequested == 1;
    }

    public Row Allocate(Seat seat)
    {
        var newVersionOfSeats = new List<Seat>();

        foreach (var currentSeat in Seats)
            if (currentSeat.SameSeatLocation(seat))
                newVersionOfSeats.Add(new Seat(seat.RowName, seat.Number, seat.PricingCategory,
                    SeatAvailability.Allocated));
            else
                newVersionOfSeats.Add(currentSeat);

        return new Row(seat.RowName, newVersionOfSeats);
    }

    protected override IEnumerable<object> GetAllAttributesToBeUsedForEquality()
    {
        return [Name, new ListByValue<Seat>(new List<Seat>(Seats))];
    }
}