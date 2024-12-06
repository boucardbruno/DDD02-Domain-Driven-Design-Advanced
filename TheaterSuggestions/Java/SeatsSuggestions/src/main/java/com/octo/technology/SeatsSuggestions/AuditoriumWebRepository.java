package com.octo.technology.SeatsSuggestions;

import com.octo.technology.ExternalDependencies.IProvideAuditoriumLayouts;
import com.octo.technology.ExternalDependencies.auditoriumlayoutrepository.AuditoriumDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class AuditoriumWebRepository implements IProvideAuditoriumLayouts {

    private final String uriAuditoriumSeatingRepository;

    public AuditoriumWebRepository() {
        this.uriAuditoriumSeatingRepository = "http://localhost:8090/";
    }

    @Override
    public AuditoriumDto getAuditoriumSeatingFor(String showId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(uriAuditoriumSeatingRepository + "/api/data_for_auditoriumSeating/"+showId, String.class);
        ObjectMapper mapper = new ObjectMapper().registerModule(new GuavaModule());
        try {
            return mapper.readValue(response.getBody(), AuditoriumDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
