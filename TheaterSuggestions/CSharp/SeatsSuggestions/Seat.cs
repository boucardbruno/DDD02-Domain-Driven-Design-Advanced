namespace SeatsSuggestions
{
    public class Seat(string rowName, uint number, PricingCategory pricingCategory, SeatAvailability seatAvailability)
    {
        public string RowName { get; } = rowName;
        public uint Number { get; } = number;
        public PricingCategory PricingCategory { get; } = pricingCategory;
        private SeatAvailability SeatAvailability { get; set; } = seatAvailability;

        public bool IsAvailable()
        {
            return SeatAvailability == SeatAvailability.Available;
        }

        public override string ToString()
        {
            return $"{RowName}{Number}";
        }

        public bool MatchCategory(PricingCategory pricingCategory)
        {
            if (pricingCategory == PricingCategory.Mixed)
            {
                return true;
            }

            return PricingCategory == pricingCategory;
        }

        public void Allocate()
        {
            if (SeatAvailability == SeatAvailability.Available)
            {
                SeatAvailability = SeatAvailability.Allocated;
            }
        }
    }
}