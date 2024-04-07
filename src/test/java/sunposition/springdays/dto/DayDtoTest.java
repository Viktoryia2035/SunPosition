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
    void testSetAndGetId() {
        DayDto dayDto = new DayDto();
        dayDto.setId(1L);
        assertEquals(1L, dayDto.getId(), "ID should be set correctly");
    }

    @Test
    void testSetDateOfSunriseSunsetWithValidValue() {
        DayDto dayDto = new DayDto();
        LocalDate testDate = LocalDate.of(2024, 4, 7);
        dayDto.setDateOfSunriseSunset(testDate);
        assertEquals(testDate, dayDto.getDateOfSunriseSunset(), "Date should be set correctly with a valid value");
    }

    @Test
    void testSetDateOfSunriseSunsetWithInvalidValue() {
        DayDto dayDto = new DayDto();
        assertThrows(IllegalArgumentException.class, () -> dayDto.setDateOfSunriseSunset(null), "Date of sunrise/sunset cannot be null");
    }

    @Test
    void testEqualsAndHashCode() {
        DayDto dayDto1 = new DayDto();
        dayDto1.setId(1L);
        dayDto1.setLocation("Test Location");
        dayDto1.setCoordinates("12.34, 56.78");
        dayDto1.setDateOfSunriseSunset(LocalDate.of(2024, 4, 7));
        dayDto1.setTimeOfSunrise(LocalTime.of(6, 0));
        dayDto1.setTimeOfSunset(LocalTime.of(18, 0));
        dayDto1.setWeatherConditions("Sunny");

        DayDto dayDto2 = new DayDto();
        dayDto2.setId(1L);
        dayDto2.setLocation("Test Location");
        dayDto2.setCoordinates("12.34, 56.78");
        dayDto2.setDateOfSunriseSunset(LocalDate.of(2024, 4, 7));
        dayDto2.setTimeOfSunrise(LocalTime.of(6, 0));
        dayDto2.setTimeOfSunset(LocalTime.of(18, 0));
        dayDto2.setWeatherConditions("Sunny");

        assertEquals(dayDto1, dayDto2, "Two DayDto objects with the same properties should be equal");
        assertEquals(dayDto1.hashCode(), dayDto2.hashCode(), "Two DayDto objects with the same properties should have the same hash code");
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
