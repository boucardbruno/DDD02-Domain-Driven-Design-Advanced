using System.Collections.Generic;

namespace SeatsSuggestions
{
    public class SeatingOptionSuggested(int partyRequested, PricingCategory priceCategory)
    {
        public PricingCategory PricingCategory { get; } = priceCategory;
        public List<Seat> Seats { get; } = new();
        public int PartyRequested { get; } = partyRequested;

        public void AddSeat(Seat seat)
        {
            Seats.Add(seat);
        }

        public bool MatchExpectation()
        {
            return Seats.Count == PartyRequested;
        }
    }
}