package ge.vako.bootcamp.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/suggestions/books")
public class SuggestionsController {

    @Value("${library-service-url}")
    String libraryServiceUrl;
    private final RestTemplate restTemplate;

    public SuggestionsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<String> getBookSuggestions() {
        String[] books = restTemplate
                .getForEntity(libraryServiceUrl + "/books", String[].class)
                .getBody();

        return books != null ? Arrays.asList(books) : Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'LIBRARY_ADMIN')")
    @PostMapping
    public String addBookSuggestion(@RequestParam String suggestion) {
        ResponseEntity<String> response = restTemplate
                .postForEntity(libraryServiceUrl + "/books/" + suggestion, null, String.class);

        return response.getBody();
    }
}

