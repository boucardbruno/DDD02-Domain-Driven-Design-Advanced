namespace SeatsSuggestions
{
    internal class SeatingOptionNotAvailable(int partyRequested, PricingCategory pricingCategory)
        : SeatingOptionSuggested(partyRequested,
            pricingCategory);
}