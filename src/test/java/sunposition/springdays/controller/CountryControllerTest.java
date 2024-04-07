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
    void testFindAllCountry() {
        List<CountryDto> countries = Arrays.asList(new CountryDto("Country1"), new CountryDto("Country2"));
        when(countryService.findAll()).thenReturn(countries);

        ResponseEntity<Object> response = countryController.findAllCountry();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Object responseBodyObject = response.getBody();

        if (responseBodyObject instanceof List<?> list) {
            boolean allAreCountryDto = true;
            for (Object obj : list) {
                if (!(obj instanceof CountryDto)) {
                    allAreCountryDto = false;
                    break;
                }
            }
            if (allAreCountryDto) {
                List<CountryDto> responseBody = (List<CountryDto>) responseBodyObject;
                assertEquals(2, responseBody.size());
                assertEquals("Country1", responseBody.get(0).getName());
                assertEquals("Country2", responseBody.get(1).getName());
            } else {
                fail("Response body is not a list of CountryDto objects");
            }
        } else {
            fail("Response body is not a list");
        }
    }

    @Test
    void testFindAllCountryInternalServerError() {
        when(countryService.findAll()).thenThrow(new HttpErrorExceptions.CustomInternalServerErrorException("Internal server error", null));

        ResponseEntity<Object> response = countryController.findAllCountry();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        assertEquals("Internal server error", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    void testSaveCountry() {
        CountryDto countryDto = new CountryDto("Country1");
        when(countryService.saveCountry(countryDto)).thenReturn(countryDto);

        ResponseEntity<Object> response = countryController.saveCountry(countryDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof CountryDto);
        assertEquals("Country1", ((CountryDto) response.getBody()).getName());
    }

    @Test
    void testSaveCountryInternalServerError() {
        CountryDto countryDto = new CountryDto("Country1");
        when(countryService.saveCountry(countryDto)).thenThrow(new HttpErrorExceptions.CustomInternalServerErrorException("Internal server error", null));

        ResponseEntity<Object> response = countryController.saveCountry(countryDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        assertEquals("Internal server error", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    void testBulkSaveDays() {
        CountryDto countryDto = new CountryDto("Country1");

        doNothing().when(countryService).bulkSaveDays(countryDto);

        ResponseEntity<String> response = countryController.bulkSaveDays(countryDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Days saved successfully", response.getBody());
    }

    @Test
    void testFindByNameCountry() {
        CountryDto countryDto = new CountryDto("Country1");
        when(countryService.findByNameCountry("Country1")).thenReturn(countryDto);

        ResponseEntity<CountryDto> response = countryController.findByNameCountry("Country1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        CountryDto responseBody = response.getBody();

        if (responseBody != null) {
            assertEquals("Country1", responseBody.getName());
        } else {
            fail("Response body is null");
        }
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
    void testUpdateCountryByName() {
        CountryDto updatedCountryDto = new CountryDto("Country1");
        when(countryService.updateCountryByName("OldName", "NewName")).thenReturn(updatedCountryDto);

        ResponseEntity<CountryDto> response = countryController.updateCountryByName("OldName", "NewName");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        CountryDto responseBody = response.getBody();

        if (responseBody != null) {
            assertEquals("Country1", responseBody.getName());
        } else {
            fail("Response body is null");
        }
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
