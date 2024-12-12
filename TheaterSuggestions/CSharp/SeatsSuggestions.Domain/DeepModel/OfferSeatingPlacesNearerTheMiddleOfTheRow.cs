using System.Collections.Generic;
using System.Linq;

namespace SeatsSuggestions.Domain.DeepModel;

public static class OfferSeatingPlacesNearerTheMiddleOfTheRow
{
    public static List<SeatWithDistance> BuildSeatingPlaceCloserTheMiddleOfTheRow(Row row,
        SuggestionRequest suggestionRequest)
    {
        return MakeSeatingPlacesWithDistance(row)
            .OrderBy(swd => swd.DistanceFromTheMiddleOfTheRow)
            .Where(swd => swd.Seat.IsAvailable())
            .Where(swd => swd.Seat.MatchCategory(suggestionRequest.PricingCategory))
            .ToList();
    }

    public static IEnumerable<SeatWithDistance> MakeSeatingPlacesWithDistance(Row row)
    {
        var middleOfTheRow = row.Seats.Count / 2;
        var seatingPlacesWithDistanceLeftSide =
            SeatingPlacesWithDistanceForLeftSide(row, middleOfTheRow);
        var seatingPlacesWithDistanceRightSide =
            SeatingPlacesWithDistanceForRightSide(row, middleOfTheRow);
        return seatingPlacesWithDistanceLeftSide.Concat(seatingPlacesWithDistanceRightSide);
    }

    private static IEnumerable<SeatWithDistance> SeatingPlacesWithDistanceForRightSide(Row row, int middleOfTheRow)
    {
        return row.Seats
            .Skip(middleOfTheRow)
            .Select(s => new SeatWithDistance(
                s,
                (int)(s.Number - ComputeTheMiddleOfTheRowForRightSide(middleOfTheRow))
            ));
    }

    private static int ComputeTheMiddleOfTheRowForRightSide(int middleOfTheRow)
    {
        return middleOfTheRow + 1;
    }

    private static IEnumerable<SeatWithDistance> SeatingPlacesWithDistanceForLeftSide(Row row, int middleOfTheRow)
    {
        return row.Seats
            .Take(middleOfTheRow)
            .Select(s => new SeatWithDistance(
                s,
                middleOfTheRow - ComputeTheMiddleOfTheRowForLeftSide(row, s)
            ));
    }

    private static int ComputeTheMiddleOfTheRowForLeftSide(Row row, Seat s)
    {
        return (int)(s.Number + Offset(row));
    }

    private static int Offset(Row row)
    {
        return RowIsEvent(row) ? 0 : -1;
    }

    private static bool RowIsEvent(Row row)
    {
        return row.Seats.Count % 2 == 0;
    }
}