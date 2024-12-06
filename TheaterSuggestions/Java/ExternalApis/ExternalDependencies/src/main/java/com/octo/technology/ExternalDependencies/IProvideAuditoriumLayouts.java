package com.octo.technology.ExternalDependencies;

import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumDto;

public interface IProvideAuditoriumLayouts {

    AuditoriumDto getAuditoriumSeatingFor(String showId);
}
