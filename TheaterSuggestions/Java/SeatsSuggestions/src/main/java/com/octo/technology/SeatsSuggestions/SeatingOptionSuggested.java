package com.octo.technology.SeatsSuggestions;

import java.util.ArrayList;
import java.util.List;

public class SeatingOptionSuggested {

    private final PricingCategory pricingCategory;
    private final List<Seat> seats = new ArrayList<>();
    private final int partyRequested;

    public SeatingOptionSuggested(int partyRequested, PricingCategory pricingCategory) {
        this.pricingCategory = pricingCategory;
        this.partyRequested = partyRequested;
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public boolean matchExpectation() {
        return seats.size() == partyRequested;
    }

    public List<Seat> seats() {
        return seats;
    }
}
