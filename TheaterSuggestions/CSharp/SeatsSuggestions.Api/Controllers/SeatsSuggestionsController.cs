using System.Threading.Tasks;
using ExternalDependencies;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using SeatsSuggestions.Domain;
using SeatsSuggestions.Infra;
using SeatsSuggestions.Infra.Adapter;

namespace SeatsSuggestions.Api.Controllers;

[Route("api/[controller]")]
[ApiController]
public class SeatsSuggestionsController(IProvideSeatSuggestionsForShows seatSuggestionsForShows) : ControllerBase
{
    // GET api/SeatsSuggestions?showId=5&party=3
    [HttpGet]
    public async Task<string> Get([FromQuery(Name = "showId")] string showId, [FromQuery(Name = "party")] int party)
    {
        var suggestions = await seatSuggestionsForShows.MakeSuggestions(showId, party);
        return JsonConvert.SerializeObject(suggestions, Formatting.Indented);
    }
}