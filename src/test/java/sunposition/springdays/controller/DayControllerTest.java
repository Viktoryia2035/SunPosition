package sunposition.springdays.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.model.Day;
import sunposition.springdays.service.DayService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class DayControllerTest {

    @InjectMocks
    private DayController dayController;

    @Mock
    private DayService dayService;

    @Test
    void testFindAllSunriseSunset() {
        List<Day> days = Arrays.asList(new Day(), new Day());
        when(dayService.findAllSunriseSunset()).thenReturn(days);

        List<Day> result = dayController.findAllSunriseSunset();

        assertEquals(2, result.size());
    }

    @Test
    void testSaveSunriseSunset_InternalServerError() {
        DayDto dayDto = new DayDto();
        Day day = new Day();
        when(dayService.saveSunriseSunset(day, "POST")).thenThrow(new RuntimeException("An error occurred"));

        ResponseEntity<DayDto> response = dayController.saveSunriseSunset(dayDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testFindByLocation_Success() {
        Day day = new Day();
        String location = "New York";
        when(dayService.findByLocation(location)).thenReturn(day);

        ResponseEntity<DayDto> response = dayController.findByLocation(location);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testFindByLocation_NotFound() {
        String location = "New York";
        when(dayService.findByLocation(location)).thenReturn(null);

        ResponseEntity<DayDto> response = dayController.findByLocation(location);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindByLocation_InternalServerError() {
        String location = "New York";
        when(dayService.findByLocation(location)).thenThrow(new RuntimeException("An error occurred"));

        ResponseEntity<DayDto> response = dayController.findByLocation(location);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testFindByCoordinates_Success() {
        Day day = new Day();
        String coordinates = "40.7128,-74.0060";
        when(dayService.findByCoordinates(coordinates)).thenReturn(day);

        ResponseEntity<DayDto> response = dayController.findByCoordinates(coordinates);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testFindByCoordinates_NotFound() {
        String coordinates = "40.7128,-74.0060";
        when(dayService.findByCoordinates(coordinates)).thenReturn(null);

        ResponseEntity<DayDto> response = dayController.findByCoordinates(coordinates);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testFindByCoordinates_InternalServerError() {
        String coordinates = "40.7128,-74.0060";
        when(dayService.findByCoordinates(coordinates)).thenThrow(new RuntimeException("An error occurred"));

        ResponseEntity<DayDto> response = dayController.findByCoordinates(coordinates);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testFindByCoordinates_BadRequest() {
        String coordinates = "";

        ResponseEntity<DayDto> response = dayController.findByCoordinates(coordinates);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteCityByCoordinates_Success() {
        String coordinates = "40.7128,-74.0060";
        doNothing().when(dayService).deleteDayByCoordinates(coordinates);

        ResponseEntity<String> response = dayController.deleteCityByCoordinates(coordinates);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The deletion was successful", response.getBody());
    }

    @Test
    void testDeleteCityByCoordinates_NotFound() {
        String coordinates = "40.7128,-74.0060";
        doThrow(new RuntimeException("City not found")).when(dayService).deleteDayByCoordinates(coordinates);

        ResponseEntity<String> response = dayController.deleteCityByCoordinates(coordinates);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error deleting city", response.getBody());
    }

    @Test
    void testUpdateSunriseSunset_Success() {
        Day day = new Day();
        String location = "New York";
        String coordinates = "40.7128,-74.0060";
        LocalDate dateOfSunriseSunset = LocalDate.now();
        when(dayService.updateSunriseSunset(location, coordinates, dateOfSunriseSunset)).thenReturn(day);

        ResponseEntity<DayDto> response = dayController.updateSunriseSunset(location, coordinates, dateOfSunriseSunset);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateSunriseSunset_NotFound() {
        String location = "New York";
        String coordinates = "40.7128,-74.0060";
        LocalDate dateOfSunriseSunset = LocalDate.now();
        when(dayService.updateSunriseSunset(location, coordinates, dateOfSunriseSunset)).thenReturn(null);

        ResponseEntity<DayDto> response = dayController.updateSunriseSunset(location, coordinates, dateOfSunriseSunset);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateSunriseSunset_InternalServerError() {
        String location = "New York";
        String coordinates = "40.7128,-74.0060";
        LocalDate dateOfSunriseSunset = LocalDate.now();
        when(dayService.updateSunriseSunset(location, coordinates, dateOfSunriseSunset)).thenThrow(new RuntimeException("An error occurred"));

        ResponseEntity<DayDto> response = dayController.updateSunriseSunset(location, coordinates, dateOfSunriseSunset);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
