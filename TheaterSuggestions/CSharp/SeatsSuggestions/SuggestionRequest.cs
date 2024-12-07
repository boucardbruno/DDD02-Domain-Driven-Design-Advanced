using System.Collections.Generic;
using Value;

namespace SeatsSuggestions;

public class SuggestionRequest : ValueType<SuggestionRequest>
{
    public SuggestionRequest(int partyRequested, PricingCategory pricingCategory)
    {
        PartyRequested = partyRequested;
        PricingCategory = pricingCategory;
    }

    public int PartyRequested { get; }
    public PricingCategory PricingCategory { get; }

    protected override IEnumerable<object> GetAllAttributesToBeUsedForEquality()
    {
        return new object[] { PartyRequested, PricingCategory };
    }

    public override string ToString()
    {
        return $"{PartyRequested}-{PricingCategory.ToString()}";
    }
}