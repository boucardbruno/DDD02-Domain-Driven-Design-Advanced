package com.octo.technology.SeatsSuggestions.DeepModel;

import java.util.*;

import com.octo.technology.SeatsSuggestions.PricingCategory;
import com.octo.technology.SeatsSuggestions.Seat;
import com.octo.technology.SeatsSuggestions.SeatAvailability;

import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class OfferSeatingPlacesAdjacentFromTheMiddleOfTheRow {
    final static int NO_DISTANCE = -1;

    public static List<Seat> buildAdjacentSeats(List<SeatWithDistance> seatsWithDistance, int partySize) {
        var previousSeatingPlaceWithDistance = new SeatWithDistance(getSeatingPlaceNull(), NO_DISTANCE);
        var potentialSeatsWithDistances = new ArrayList<SeatWithDistance>();
        var adjacentSeatsList = new ArrayList<AdjacentSeats>();

        var seatingPlaceSorted = seatsWithDistance.stream().sorted(comparing(s -> s.seat().number()))
                .collect(Collectors.toList());

        for (var seatingPlaceWithDistance : seatingPlaceSorted) {
            if (areAdjacent(seatingPlaceWithDistance, previousSeatingPlaceWithDistance)) {
                addSeatingPlacesToPotentials(potentialSeatsWithDistances, seatingPlaceWithDistance, previousSeatingPlaceWithDistance);
            } else {
                if (isMatching(potentialSeatsWithDistances, partySize)) {
                    CollectAdjacentSeatByDistance(partySize, potentialSeatsWithDistances, adjacentSeatsList);
                }
                potentialSeatsWithDistances = new ArrayList<>();
            }
            previousSeatingPlaceWithDistance = seatingPlaceWithDistance;
        }

        if (!potentialSeatsWithDistances.isEmpty())
        {
            CollectAdjacentSeatByDistance(partySize, potentialSeatsWithDistances, adjacentSeatsList);
        }

        if (!adjacentSeatsList.isEmpty()) {
            return computeTheBestAdjacentSeatsFromTheMiddleOfTheRow(adjacentSeatsList);
        }
        return Collections.emptyList();
    }

    private static void CollectAdjacentSeatByDistance(int partySize, ArrayList<SeatWithDistance> potentialSeatsWithDistances, ArrayList<AdjacentSeats> adjacentSeatsList) {
        var seatWithDistances = new ArrayList<SeatWithDistance>();

        for (var seatingPlaceWithDistance : potentialSeatsWithDistances.stream().sorted(comparing(SeatWithDistance::distanceFromTheMiddleOfTheRow)).collect(Collectors.toList()))
        {
            seatWithDistances.add(seatingPlaceWithDistance);

            if (seatWithDistances.size() == partySize)
            {
                adjacentSeatsList.add(new AdjacentSeats(seatWithDistances.stream().sorted(comparing(s-> s.seat().number())).collect(Collectors.toList())));
                seatWithDistances = new ArrayList<>();
            }
        }
    }

    private static Seat getSeatingPlaceNull() {
        return new Seat("#", -1, PricingCategory.Mixed, SeatAvailability.Available);
    }

    private static boolean isMatching(ArrayList<SeatWithDistance> potentialSeatsWithDistances, int partySize) {
        return potentialSeatsWithDistances.size() >= partySize;
    }

    private static void addSeatingPlacesToPotentials(ArrayList<SeatWithDistance> potentialSeatsWithDistances, SeatWithDistance seatingPlaceWithDistance, SeatWithDistance previousSeatingPlaceWithDistance) {
        if (potentialSeatsWithDistances.isEmpty()) {
            potentialSeatsWithDistances.addAll(Arrays.asList(previousSeatingPlaceWithDistance, seatingPlaceWithDistance));
        } else {
            potentialSeatsWithDistances.add(seatingPlaceWithDistance);
        }
    }

    private static boolean areAdjacent(SeatWithDistance seatingPlaceWithDistance, SeatWithDistance previousSeatingPlaceWithDistance) {
        return previousSeatingPlaceWithDistance.seat().number() + 1 == seatingPlaceWithDistance.seat().number();
    }

    private static List<Seat> computeTheBestAdjacentSeatsFromTheMiddleOfTheRow(ArrayList<AdjacentSeats> adjacentSeatsList) {
        SortedMap<Integer, List<SeatWithDistance>> adjacentSeatsNearerTheMiddle = new TreeMap<>();

        for (var adjacentSeats : adjacentSeatsList) {
            var sumOfDistanceFromTheMiddle = adjacentSeats.seatsWithDistance.stream()
                    .map(SeatWithDistance::distanceFromTheMiddleOfTheRow)
                    .reduce(Integer::sum).orElse(0);
            if (adjacentSeatsNearerTheMiddle.containsKey(sumOfDistanceFromTheMiddle)) {
                sumOfDistanceFromTheMiddle++;
            }
            adjacentSeatsNearerTheMiddle.put(sumOfDistanceFromTheMiddle, adjacentSeats.seatsWithDistance);
        }
        return adjacentSeatsNearerTheMiddle
                .get(adjacentSeatsNearerTheMiddle.firstKey()).stream()
                .map(SeatWithDistance::seat)
                .collect(Collectors.toList());
    }
}