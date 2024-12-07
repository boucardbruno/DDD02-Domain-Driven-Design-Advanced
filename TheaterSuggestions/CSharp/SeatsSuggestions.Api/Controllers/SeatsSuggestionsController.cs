using System.Threading.Tasks;
using ExternalDependencies;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;

namespace SeatsSuggestions.Api.Controllers;

[Route("api/[controller]")]
[ApiController]
public class SeatsSuggestionsController(
    IProvideAuditoriumLayouts auditoriumSeatingRepository,
    IProvideCurrentReservations seatReservationsProvider)
    : ControllerBase
{
    // GET api/SeatsSuggestions?showId=5&party=3
    [HttpGet]
    public async Task<string> Get([FromQuery(Name = "showId")] string showId, [FromQuery(Name = "party")] int party)
    {
        var seatAllocator =
            new SeatAllocator(new AuditoriumSeatingAdapter(auditoriumSeatingRepository, seatReservationsProvider));
        var suggestions = await seatAllocator.MakeSuggestions(showId, party);

        var jsonSuggestions = JsonConvert.SerializeObject(suggestions, Formatting.Indented);
        return jsonSuggestions;
    }
}