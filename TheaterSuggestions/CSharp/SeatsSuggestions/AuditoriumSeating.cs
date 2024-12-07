using System.Collections.Generic;

namespace SeatsSuggestions
{
    public class AuditoriumSeating(Dictionary<string, Row> rows)
    {
        public IReadOnlyDictionary<string, Row> Rows => rows;

        public SeatingOptionSuggested SuggestSeatingOptionFor(int partyRequested, PricingCategory pricingCategory)
        {
            foreach (var row in rows.Values)
            {
                var seatOptionsSuggested = row.SuggestSeatingOption(partyRequested, pricingCategory);

                if (seatOptionsSuggested.MatchExpectation())
                {
                    return seatOptionsSuggested;
                }
            }
            return new SeatingOptionNotAvailable(partyRequested, pricingCategory);
        }
    }
}