using System.Collections.Generic;
using System.Linq;

namespace SeatsSuggestions
{
    /// <summary>
    ///     Occurs when a Suggestion is made.
    /// </summary>
    public class SuggestionMade(int partyRequested, PricingCategory pricingCategory, List<Seat> seats)
    {
        public int PartyRequested { get; } = partyRequested;
        public PricingCategory PricingCategory { get; } = pricingCategory;

        public IReadOnlyList<Seat> SuggestedSeats => seats;

        public IEnumerable<string> SeatNames()
        {
            return seats.Select(s => s.ToString());
        }

        public bool MatchExpectation()
        {
            return seats.Count == PartyRequested;
        }
    }
}