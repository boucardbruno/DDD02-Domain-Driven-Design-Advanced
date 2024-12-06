package com.octo.technology.SeatsSuggestions;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

public class SuggestionMade {

    private final ImmutableList<Seat> suggestedSeats;
    private final int partyRequested;
    private final PricingCategory pricingCategory;

    public SuggestionMade(List<Seat> suggestedSeats, int partyRequested, PricingCategory pricingCategory) {
        this.suggestedSeats = ImmutableList.copyOf(suggestedSeats);
        this.partyRequested = partyRequested;
        this.pricingCategory = pricingCategory;
    }

    public List<String> seatNames() {
        return suggestedSeats.stream().map(Seat::toString).collect(Collectors.toList());
    }

    public boolean MatchExpectation() {
        return suggestedSeats.size() == partyRequested;
    }

    public PricingCategory pricingCategory() {
        return pricingCategory;
    }
}