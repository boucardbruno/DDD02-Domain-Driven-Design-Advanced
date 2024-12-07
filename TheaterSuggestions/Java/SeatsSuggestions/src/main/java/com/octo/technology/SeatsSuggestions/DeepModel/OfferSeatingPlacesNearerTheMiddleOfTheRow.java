package com.octo.technology.SeatsSuggestions.DeepModel;

import com.octo.technology.SeatsSuggestions.Row;
import com.octo.technology.SeatsSuggestions.Seat;
import com.octo.technology.SeatsSuggestions.SuggestionRequest;

import java.util.*;
import java.util.stream.Collectors;

public  class OfferSeatingPlacesNearerTheMiddleOfTheRow
{
    public static List<SeatWithDistance> buildSeatingPlaceCloserTheMiddleOfTheRow(Row row, SuggestionRequest suggestionRequest)
    {
        return makeSeatingPlacesWithDistance(row)
                .stream()
                .sorted(Comparator.comparingInt(SeatWithDistance::distanceFromTheMiddleOfTheRow))
                .filter(swd -> swd.seat().isAvailable())
                .filter(swd -> swd.seat().matchCategory(suggestionRequest.pricingCategory()))
                .collect(Collectors.toList());
    }

    public static List<SeatWithDistance> makeSeatingPlacesWithDistance(Row row)
    {
        int middleOfTheRow = row.seats().size() / 2;
        List<SeatWithDistance> seatingPlacesWithDistanceLeftSide =
                seatingPlacesWithDistanceForLeftSide(row, middleOfTheRow);
        List<SeatWithDistance> seatingPlacesWithDistanceRightSide =
                SeatingPlacesWithDistanceForRightSide(row, middleOfTheRow);

        seatingPlacesWithDistanceLeftSide.addAll(seatingPlacesWithDistanceRightSide);
        return seatingPlacesWithDistanceLeftSide;
    }

    private static List<SeatWithDistance> SeatingPlacesWithDistanceForRightSide(Row row, int middleOfTheRow)
    {
        return row.seats()
                .stream()
                .skip(middleOfTheRow)
                .map(s -> new SeatWithDistance(
                        s,
                        s.number() -  computeTheMiddleOfTheRowForRightSide(middleOfTheRow)
                ))
                .collect(Collectors.toList());
    }

    private static int computeTheMiddleOfTheRowForRightSide(int middleOfTheRow)
    {
        return (middleOfTheRow + 1);
    }

    private static List<SeatWithDistance> seatingPlacesWithDistanceForLeftSide(Row row, int middleOfTheRow)
    {
        return row.seats()
                .stream()
                .limit(middleOfTheRow)
                .map(s -> new SeatWithDistance(
                        s,
                        middleOfTheRow - ComputeTheMiddleOfTheRowForLeftSide(row, s)
                ))
                .collect(Collectors.toList());
    }

    private static int ComputeTheMiddleOfTheRowForLeftSide(Row row, Seat s)
    {
        return s.number() + Offset(row);
    }

    private static int Offset(Row row)
    {
        return RowIsEvent(row) ? 0 : -1;
    }

    private static boolean RowIsEvent(Row row)
    {
        return row.seats().size() % 2 == 0;
    }
}