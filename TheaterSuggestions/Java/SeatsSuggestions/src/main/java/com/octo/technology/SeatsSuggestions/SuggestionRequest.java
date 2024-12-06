package com.octo.technology.SeatsSuggestions;

import lombok.Value;

@Value
public class SuggestionRequest {

    int partyRequested;
    PricingCategory pricingCategory;
}
