package com.octo.technology.ExternalDependencies.auditoriumlayoutrepository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class SeatDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Category")
    private int category;
}
