package sunposition.springdays.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sunposition.springdays.dto.CountryDto;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.ErrorResponse;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.service.CountryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    @Test
    void testFindAllCountryInternalServerError() {
        when(countryService.findAll()).thenThrow(new HttpErrorExceptions.CustomInternalServerErrorException("Internal server error", null));

        ResponseEntity<Object> response = countryController.findAllCountry();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        assertEquals("Internal server error", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    void testSaveCountryInternalServerError() {
        CountryDto countryDto = new CountryDto();
        when(countryService.saveCountry(countryDto)).thenThrow(new HttpErrorExceptions.CustomInternalServerErrorException("Internal server error", null));

        ResponseEntity<Object> response = countryController.saveCountry(countryDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        assertEquals("Internal server error", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    void testBulkSaveDays() {
        List<CountryDto> countryDtoList = new ArrayList<>();
        CountryDto countryDto1 = new CountryDto();
        countryDto1.setName("Страна 1");
        countryDto1.setCapital("Столица 1");
        countryDto1.setPopulation(1000000L);
        countryDto1.setLanguage("Язык 1");
        countryDto1.setDays(Arrays.asList(new DayDto(), new DayDto()));
        countryDtoList.add(countryDto1);

        CountryDto countryDto2 = new CountryDto();
        countryDto2.setName("Страна 2");
        countryDto2.setCapital("Столица 2");
        countryDto2.setPopulation(2000000L);
        countryDto2.setLanguage("Язык 2");
        countryDto2.setDays(Arrays.asList(new DayDto(), new DayDto()));
        countryDtoList.add(countryDto2);

        doNothing().when(countryService).bulkSaveDays(anyList());

        ResponseEntity<String> response = countryController.bulkSaveDays(countryDtoList);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Days saved successfully", response.getBody());
    }


    @Test
    void testFindByNameCountryNotFound() {
        when(countryService.findByNameCountry("CountryNotFound")).thenReturn(null);

        ResponseEntity<CountryDto> response = countryController.findByNameCountry("CountryNotFound");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteCountryById() {
        doNothing().when(countryService).deleteCountryById(1L);

        ResponseEntity<String> response = countryController.deleteCountryById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The deletion was successful", response.getBody());
    }

    @Test
    void testDeleteCountryByIdNotFound() {
        doThrow(new EntityNotFoundException("Country not found")).when(countryService).deleteCountryById(1L);

        ResponseEntity<String> response = countryController.deleteCountryById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindByCountryNameAndWeatherConditions() {
        List<DayDto> dayDtos = Arrays.asList(new DayDto(), new DayDto());
        when(countryService.findByCountryNameAndWeatherConditions("Country1", "Sunny")).thenReturn(dayDtos);

        ResponseEntity<List<DayDto>> response = countryController.findByCountryNameAndWeatherConditions("Country1", "Sunny");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<DayDto> responseBody = response.getBody();

        if (responseBody != null) {
            assertEquals(2, responseBody.size());
        } else {
            fail("Response body is null");
        }
    }
}
