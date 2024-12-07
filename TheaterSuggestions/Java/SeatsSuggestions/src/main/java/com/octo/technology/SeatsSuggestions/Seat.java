package com.octo.technology.SeatsSuggestions;

public class Seat {

    private final String rowName;
    private final int number;
    private final PricingCategory pricingCategory;
    private SeatAvailability seatAvailability;

    public Seat(String rowName, int number, PricingCategory pricingCategory, SeatAvailability seatAvailability) {
        this.rowName = rowName;
        this.number = number;
        this.pricingCategory = pricingCategory;
        this.seatAvailability = seatAvailability;
    }

    public boolean isAvailable() {
        return seatAvailability == SeatAvailability.Available;
    }

    public boolean matchCategory(PricingCategory pricingCategory) {
        if (pricingCategory == PricingCategory.Mixed) {
            return true;
        }

        return this.pricingCategory == pricingCategory;
    }

    public void allocate() {
        if (seatAvailability == SeatAvailability.Available) {
            seatAvailability = SeatAvailability.Allocated;
        }
    }

    @Override
    public String toString() {
        return rowName + number;
    }
}
