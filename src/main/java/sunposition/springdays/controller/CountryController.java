package sunposition.springdays.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunposition.springdays.dto.CountryDto;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.ErrorResponse;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.service.CountryService;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v2/country")
@AllArgsConstructor
public class CountryController {

    private final CountryService service;

    static final Logger LOGGER = LogManager.getLogger(CountryController.class);

    @GetMapping
    public ResponseEntity<Object> findAllCountry() {
        try {
            LOGGER.info("Finding all countries");
            List<CountryDto> countries = service.findAll();
            LOGGER.info("Found {} countries", countries.size());
            return ResponseEntity.ok(countries);
        } catch (HttpErrorExceptions.CustomInternalServerErrorException e) {
            LOGGER.error("Error fetching countries", e);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveCountry")
    public ResponseEntity<Object> saveCountry(
            @Valid @RequestBody final CountryDto countryDto) {
        try {
            LOGGER.info("Saving a country");
            CountryDto savedCountryDto = service.saveCountry(countryDto);
            LOGGER.info("Country saved successfully");
            return ResponseEntity.
                    status(HttpStatus.CREATED).body(savedCountryDto);
        } catch (RuntimeException e) {
            LOGGER.error("Error saving country", e);
            ErrorResponse errorResponse =
                    new ErrorResponse(e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse,
                    errorResponse.getStatus());
        }
    }

    @PostMapping("/bulkSaveDays")
    public ResponseEntity<String> bulkSaveDays(
            @Valid @RequestBody final CountryDto countryDto) {
        LOGGER.info("Saving multiple days for a country");
        service.bulkSaveDays(countryDto);
        LOGGER.info("Days saved successfully for a country");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Days saved successfully");
    }


    @GetMapping("/findName")
    public ResponseEntity<CountryDto> findByNameCountry(
            @RequestParam final String name) {
        LOGGER.info("Finding country by name");
        CountryDto countryDto = service.findByNameCountry(name);
        if (countryDto != null) {
            LOGGER.info("Country found");
            return ResponseEntity.ok(countryDto);
        } else {
            LOGGER.info("Country not found");
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteCountryById(
            @RequestParam final Long id) {
        LOGGER.info("Deleting country by id: {}", id);
        try {
            service.deleteCountryById(id);
            LOGGER.info("Country deleted successfully: {}", id);
            return ResponseEntity.ok("The deletion was successful");
        } catch (EntityNotFoundException e) {
            LOGGER.error("Country not found: {}", id);
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/updateByName")
    public ResponseEntity<CountryDto> updateCountryByName(
            @RequestParam final String name,
            @RequestParam final String newName) {
        LOGGER.info("Updating country name");
        CountryDto updatedCountryDto = service.
                updateCountryByName(name, newName);
        LOGGER.info("Country updated successfully: {}",
                updatedCountryDto.getName());
        return ResponseEntity.ok(updatedCountryDto);
    }

    @GetMapping("/findByNameAndWeather")
    public ResponseEntity<List<DayDto>> findByCountryNameAndWeatherConditions(
            @RequestParam final String countryName,
            @RequestParam final String weatherConditions) {
        LOGGER.info("Finding days by country name and weather conditions");
        List<DayDto> dayDto = service.
                findByCountryNameAndWeatherConditions(
                        countryName, weatherConditions);
        LOGGER.info("Found {} days", dayDto.size());
        return ResponseEntity.ok(dayDto);
    }
}
