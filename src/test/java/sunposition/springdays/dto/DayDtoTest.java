package sunposition.springdays.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DayDtoTest {

    @Test
    void testDayDtoCreation() {
        DayDto dayDto = new DayDto();
        assertNotNull(dayDto, "DayDto object should not be null");
    }

    @Test
    void testSetAndGetLocation() {
        DayDto dayDto = new DayDto();
        dayDto.setLocation("Test Location");
        assertEquals("Test Location", dayDto.getLocation(), "Location should be set correctly");
    }

    @Test
    void testSetAndGetCoordinates() {
        DayDto dayDto = new DayDto();
        dayDto.setCoordinates("12.34, 56.78");
        assertEquals("12.34, 56.78", dayDto.getCoordinates(), "Coordinates should be set correctly");
    }

    @Test
    void testSetAndGetDateOfSunriseSunset() {
        DayDto dayDto = new DayDto();
        LocalDate testDate = LocalDate.of(2024, 4, 7);
        dayDto.setDateOfSunriseSunset(testDate);
        assertEquals(testDate, dayDto.getDateOfSunriseSunset(), "Date should be set correctly");
    }

    @Test
    void testSetAndGetTimeOfSunrise() {
        DayDto dayDto = new DayDto();
        LocalTime testTime = LocalTime.of(6, 0);
        dayDto.setTimeOfSunrise(testTime);
        assertEquals(testTime, dayDto.getTimeOfSunrise(), "Sunrise time should be set correctly");
    }

    @Test
    void testSetAndGetTimeOfSunset() {
        DayDto dayDto = new DayDto();
        LocalTime testTime = LocalTime.of(18, 0);
        dayDto.setTimeOfSunset(testTime);
        assertEquals(testTime, dayDto.getTimeOfSunset(), "Sunset time should be set correctly");
    }

    @Test
    void testSetAndGetWeatherConditions() {
        DayDto dayDto = new DayDto();
        dayDto.setWeatherConditions("Sunny");
        assertEquals("Sunny", dayDto.getWeatherConditions(), "Weather conditions should be set correctly");
    }

    @Test
    void testNotNullValidation() {
        DayDto dayDto = new DayDto();
        assertThrows(IllegalArgumentException.class, () -> dayDto.setDateOfSunriseSunset(null), "Date of sunrise/sunset cannot be null");
    }
}
