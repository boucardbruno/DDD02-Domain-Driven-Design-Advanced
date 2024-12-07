namespace SeatsSuggestions
{
    /// <summary>
    ///     Occurs when a Suggestion that does not meet expectation is made.
    /// </summary>
    public class SuggestionNotAvailable(string showId, int partyRequested) : SuggestionsMade(showId, partyRequested);
}