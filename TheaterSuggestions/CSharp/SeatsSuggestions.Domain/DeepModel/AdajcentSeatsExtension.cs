using System.Collections.Generic;

namespace SeatsSuggestions.Domain.DeepModel;

internal static class AdjacentSeatsExtension
{
    public static bool IsMatchingThe(this List<SeatWithDistance> potentialSeatsWithDistances, int partySize)
    {
        return potentialSeatsWithDistances.Count >= partySize;
    }

    public static bool IsAdjacentWith(this SeatWithDistance previousSeatingPlaceWithDistance,
        SeatWithDistance seatingPlaceWithDistance)
    {
        return previousSeatingPlaceWithDistance.Seat.Number + 1 == seatingPlaceWithDistance.Seat.Number;
    }

    public static void Add(this List<SeatWithDistance> potentialSeatsWithDistances,
        SeatWithDistance previousSeatingPlaceWithDistance, SeatWithDistance seatingPlaceWithDistance)
    {
        if (potentialSeatsWithDistances.Count == 0)
            potentialSeatsWithDistances.AddRange(new List<SeatWithDistance>
            {
                previousSeatingPlaceWithDistance, seatingPlaceWithDistance
            });
        else
            potentialSeatsWithDistances.Add(seatingPlaceWithDistance);
    }
}