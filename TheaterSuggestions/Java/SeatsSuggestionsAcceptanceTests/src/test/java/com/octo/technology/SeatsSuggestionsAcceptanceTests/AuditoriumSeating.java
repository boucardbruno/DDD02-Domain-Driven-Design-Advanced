package com.octo.technology.SeatsSuggestionsAcceptanceTests;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class AuditoriumSeating {

    private final ImmutableMap<String, Row> rows;

    public AuditoriumSeating(Map<String, Row> rows) {
        this.rows = ImmutableMap.copyOf(rows);
    }

    public ImmutableMap<String, Row> rows() {
        return rows;
    }
}