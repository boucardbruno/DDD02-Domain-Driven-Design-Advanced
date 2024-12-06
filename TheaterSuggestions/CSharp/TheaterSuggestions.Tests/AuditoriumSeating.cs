using System.Collections.Generic;

namespace SeatsSuggestions.Tests
{
    public class AuditoriumSeating(Dictionary<string, Row> rows)
    {
        public readonly Dictionary<string, Row> Rows = rows;
    }
}