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

    @Test
    void testEqualsWithNull() {
        DayDto dayDto = new DayDto();
        assertNotEquals(null, dayDto, "DayDto object should not be equal to null");
    }

    @Test
    void testEqualsWithDifferentClass() {
        DayDto dayDto = new DayDto();
        String differentClassObject = "Different Class Object";
        assertNotEquals(dayDto, differentClassObject, "DayDto object should not be equal to object of different class");
    }

    @Test
    void testEqualsWithSameObject() {
        DayDto dayDto = new DayDto();
        assertEquals(dayDto, dayDto, "DayDto object should be equal to itself");
    }

    @Test
    void testEqualsWithDifferentId() {
        DayDto dayDto1 = new DayDto();
        dayDto1.setId(1L);
        DayDto dayDto2 = new DayDto();
        dayDto2.setId(2L);
        assertNotEquals(dayDto1, dayDto2, "DayDto objects with different IDs should not be equal");
    }

    @Test
    void testEqualsWithDifferentLocation() {
        DayDto dayDto1 = new DayDto();
        dayDto1.setLocation("Location1");
        DayDto dayDto2 = new DayDto();
        dayDto2.setLocation("Location2");
        assertNotEquals(dayDto1, dayDto2, "DayDto objects with different locations should not be equal");
    }

    @Test
    void testEqualsWithDifferentCoordinates() {
        DayDto dayDto1 = new DayDto();
        dayDto1.setCoordinates("12.34, 56.78");
        DayDto dayDto2 = new DayDto();
        dayDto2.setCoordinates("12.35, 56.79");
        assertNotEquals(dayDto1, dayDto2, "DayDto objects with different coordinates should not be equal");
    }

    @Test
    void testEqualsWithDifferentDateOfSunriseSunset() {
        DayDto dayDto1 = new DayDto();
        dayDto1.setDateOfSunriseSunset(LocalDate.of(2024, 4, 7));
        DayDto dayDto2 = new DayDto();
        dayDto2.setDateOfSunriseSunset(LocalDate.of(2024, 4, 8));
        assertNotEquals(dayDto1, dayDto2, "DayDto objects with different dates of sunrise/sunset should not be equal");
    }

    @Test
    void testEqualsWithDifferentTimeOfSunrise() {
        DayDto dayDto1 = new DayDto();
        dayDto1.setTimeOfSunrise(LocalTime.of(6, 0));
        DayDto dayDto2 = new DayDto();
        dayDto2.setTimeOfSunrise(LocalTime.of(7, 0));
        assertNotEquals(dayDto1, dayDto2, "DayDto objects with different times of sunrise should not be equal");
    }

    @Test
    void testEqualsWithDifferentTimeOfSunset() {
        DayDto dayDto1 = new DayDto();
        dayDto1.setTimeOfSunset(LocalTime.of(18, 0));
        DayDto dayDto2 = new DayDto();
        dayDto2.setTimeOfSunset(LocalTime.of(19, 0));
        assertNotEquals(dayDto1, dayDto2, "DayDto objects with different times of sunset should not be equal");
    }

    @Test
    void testEqualsWithDifferentWeatherConditions() {
        DayDto dayDto1 = new DayDto();
        dayDto1.setWeatherConditions("Sunny");
        DayDto dayDto2 = new DayDto();
        dayDto2.setWeatherConditions("Cloudy");
        assertNotEquals(dayDto1, dayDto2, "DayDto objects with different weather conditions should not be equal");
    }

}
