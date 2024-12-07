using System.Collections.Generic;
using System.Linq;

namespace SeatsSuggestions.DeepModel;

public static class OfferSeatingPlacesAdjacentFromTheMiddleOfTheRow
{
    private const int NoDistance = -1;

    public static IEnumerable<Seat> BuildAdjacentSeats(IEnumerable<SeatWithDistance> seatsWithDistance,
        SuggestionRequest suggestionRequest)
    {
        var previousSeatingPlaceWithDistance = new SeatWithDistance(new SeatNull(), NoDistance);
        var potentialSeatsWithDistances = new List<SeatWithDistance>();
        var adjacentSeatsList = new List<AdjacentSeats>();

        var seatingPlaceWithDistances = seatsWithDistance.OrderBy(s => s.Seat.Number);
        foreach (var seatingPlaceWithDistance in seatingPlaceWithDistances)
        {
            if (previousSeatingPlaceWithDistance.IsAdjacentWith(seatingPlaceWithDistance))
            {
                potentialSeatsWithDistances.Add(previousSeatingPlaceWithDistance, seatingPlaceWithDistance);
            }
            else
            {
                if (potentialSeatsWithDistances.IsMatchingThe(suggestionRequest.PartyRequested))
                    CollectAdjacentSeatByDistance(suggestionRequest.PartyRequested, potentialSeatsWithDistances,
                        adjacentSeatsList);
                potentialSeatsWithDistances = [];
            }

            previousSeatingPlaceWithDistance = seatingPlaceWithDistance;
        }

        if (potentialSeatsWithDistances.Any())
            CollectAdjacentSeatByDistance(suggestionRequest.PartyRequested, potentialSeatsWithDistances,
                adjacentSeatsList);

        if (adjacentSeatsList.Any()) return ComputeTheBest(adjacentSeatsList);

        return [];
    }

    private static IEnumerable<Seat> ComputeTheBest(List<AdjacentSeats> adjacentSeatsList)
    {
        var adjacentSeatsNearerTheMiddle = ComputeTheBestAdjacentSeatsFromTheMiddleOfTheRow(adjacentSeatsList);
        return adjacentSeatsNearerTheMiddle
            .FirstOrDefault()
            .Value
            .Select(swd => swd.Seat).ToList();
    }

    private static void CollectAdjacentSeatByDistance(int partySize,
        List<SeatWithDistance> potentialSeatsWithDistances,
        List<AdjacentSeats> adjacentSeatsList)
    {
        var seatWithDistances = new List<SeatWithDistance>();
        foreach (var seatingPlaceWithDistance in potentialSeatsWithDistances.OrderBy(s =>
                     s.DistanceFromTheMiddleOfTheRow))
        {
            seatWithDistances.Add(seatingPlaceWithDistance);
            if (seatWithDistances.Count == partySize)
            {
                adjacentSeatsList.Add(new AdjacentSeats(seatWithDistances.OrderBy(s => s.Seat.Number)));
                seatWithDistances = [];
            }
        }
    }

    private static SortedDictionary<int, List<SeatWithDistance>> ComputeTheBestAdjacentSeatsFromTheMiddleOfTheRow(
        List<AdjacentSeats> adjacentSeatsList)
    {
        var adjacentSeatsNearerTheMiddle = new SortedDictionary<int, List<SeatWithDistance>>();
        foreach (var adjacentSeats in adjacentSeatsList)
        {
            var sumOfDistanceFromTheMiddle = adjacentSeats.SeatsWithDistance
                .Select(swd => swd.DistanceFromTheMiddleOfTheRow)
                .Sum();
            // we have several AdjacentSeats with the same distance
            if (adjacentSeatsNearerTheMiddle.ContainsKey(sumOfDistanceFromTheMiddle)) sumOfDistanceFromTheMiddle++;
            adjacentSeatsNearerTheMiddle.Add(sumOfDistanceFromTheMiddle, adjacentSeats.SeatsWithDistance);
        }

        return adjacentSeatsNearerTheMiddle;
    }
}