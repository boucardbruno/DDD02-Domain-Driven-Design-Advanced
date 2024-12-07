using System.Collections.Generic;

namespace SeatsSuggestions;

public class SeatingOptionSuggested
{
    public SeatingOptionSuggested(SuggestionRequest suggestionRequest)
    {
        PartyRequested = suggestionRequest.PartyRequested;
        PricingCategory = suggestionRequest.PricingCategory;
    }

    public PricingCategory PricingCategory { get; }
    public List<Seat> Seats { get; } = new();
    public int PartyRequested { get; }

    public void AddSeat(Seat seat)
    {
        Seats.Add(seat);
    }

    public bool MatchExpectation()
    {
        return Seats.Count == PartyRequested;
    }
}