package com.octo.technology.AuditoriumLayoutApi.controllers;


import com.octo.technology.ExternalDependencies.IProvideAuditoriumLayouts;
import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/data_for_auditoriumSeating")
public class AuditoriumSeatingController {

    private final IProvideAuditoriumLayouts provideAuditoriumLayouts;

    public AuditoriumSeatingController(IProvideAuditoriumLayouts provideAuditoriumLayouts) {
        this.provideAuditoriumLayouts = provideAuditoriumLayouts;
    }

    // GET api/data_for_auditoriumSeating/5
    @GetMapping(value = "/{showId}", produces = "application/json")
    public AuditoriumDto get(@PathVariable String showId) {
        return provideAuditoriumLayouts.getAuditoriumSeatingFor(showId);
    }
}
