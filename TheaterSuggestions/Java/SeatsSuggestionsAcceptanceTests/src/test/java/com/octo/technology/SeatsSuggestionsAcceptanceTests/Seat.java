package com.octo.technology.SeatsSuggestionsAcceptanceTests;

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


    public void updateCategory(SeatAvailability seatAvailability) {
        this.seatAvailability = seatAvailability;
    }

    @Override
    public String toString() {
        return rowName + number;
    }
}