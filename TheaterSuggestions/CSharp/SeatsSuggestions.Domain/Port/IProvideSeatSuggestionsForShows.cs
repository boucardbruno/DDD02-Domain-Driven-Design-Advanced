using System.Threading.Tasks;

namespace SeatsSuggestions.Domain;

public interface IProvideSeatSuggestionsForShows
{
    Task<SuggestionsMade> MakeSuggestions(string showId, int partyRequested);
}