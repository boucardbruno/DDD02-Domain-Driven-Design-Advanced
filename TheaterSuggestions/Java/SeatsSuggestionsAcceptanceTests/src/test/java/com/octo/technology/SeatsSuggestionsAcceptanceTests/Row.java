package com.octo.technology.SeatsSuggestionsAcceptanceTests;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private String name;
    private final List<Seat> seats = new ArrayList<>();

    public List<Seat> seats() {
        return seats;
    }
}