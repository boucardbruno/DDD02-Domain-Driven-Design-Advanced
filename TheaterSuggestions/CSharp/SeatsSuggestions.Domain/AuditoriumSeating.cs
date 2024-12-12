using System.Collections.Generic;
using SeatsSuggestions.Domain;
using Value;
using Value.Shared;

namespace SeatsSuggestions;

public class AuditoriumSeating(Dictionary<string, Row> rows) : ValueType<AuditoriumSeating>
{
    public IReadOnlyDictionary<string, Row> Rows => rows;

    public SeatingOptionSuggested SuggestSeatingOptionFor(SuggestionRequest suggestionRequest)
    {
        foreach (var row in rows.Values)
        {
            var seatingOption = row.SuggestSeatingOption(suggestionRequest);

            if (seatingOption.MatchExpectation()) return seatingOption;
        }

        return new SeatingOptionNotAvailable(suggestionRequest);
    }

    public AuditoriumSeating Allocate(SeatingOptionSuggested seatingOptionSuggested)
    {
        // Update the seat references in the Auditorium
        var newAuditorium = AllocateSeats(seatingOptionSuggested.Seats);

        return newAuditorium;
    }

    protected override IEnumerable<object> GetAllAttributesToBeUsedForEquality()
    {
        return new object[] { new DictionaryByValue<string, Row>(rows) };
    }

    private AuditoriumSeating AllocateSeats(IEnumerable<Seat> updatedSeats)
    {
        var newVersionOfRows = new Dictionary<string, Row>(rows);

        foreach (var updatedSeat in updatedSeats)
        {
            var formerRow = newVersionOfRows[updatedSeat.RowName];
            var newVersionOfRow = formerRow.Allocate(updatedSeat);
            newVersionOfRows[updatedSeat.RowName] = newVersionOfRow;
        }

        return new AuditoriumSeating(newVersionOfRows);
    }
}