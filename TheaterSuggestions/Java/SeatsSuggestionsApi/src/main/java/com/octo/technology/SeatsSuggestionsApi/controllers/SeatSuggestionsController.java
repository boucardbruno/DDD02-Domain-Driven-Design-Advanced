package com.octo.technology.SeatsSuggestionsApi.controllers;

import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumLayoutRepository;
import com.octo.technology.ExternalDependencies.reservationsprovider.ReservationsProvider;
import com.octo.technology.SeatsSuggestions.AuditoriumSeatingAdapter;
import com.octo.technology.SeatsSuggestions.SeatsAllocator;
import com.octo.technology.SeatsSuggestions.SuggestionsMade;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/SeatsSuggestions")
public class SeatSuggestionsController {


    public SeatSuggestionsController() {
    }

    // GET api/SeatsSuggestions?showId=5&party=3
    @GetMapping(produces = "application/json")
    public SuggestionsMade get(@RequestParam String showId, @RequestParam int party) throws IOException {

        SeatsAllocator seatsAllocator = new SeatsAllocator(new AuditoriumSeatingAdapter(new AuditoriumLayoutRepository(), new ReservationsProvider()));
        return seatsAllocator.makeSuggestions(showId, party);
    }
}
