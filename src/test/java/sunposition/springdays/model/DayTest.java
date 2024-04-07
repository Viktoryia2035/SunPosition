package sunposition.springdays.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DayTest {

    @Test
    void testDayCreation() {
        Day day = new Day();
        assertNotNull(day, "Day object should not be null");
    }

    @Test
    void testSetAndGetLocation() {
        Day day = new Day();
        day.setLocation("Test Location");
        assertEquals("Test Location", day.getLocation(), "Location should be set correctly");
    }

    @Test
    void testSetAndGetDateOfSunriseSunset() {
        Day day = new Day();
        LocalDate testDate = LocalDate.of(2024, 4, 7);
        day.setDateOfSunriseSunset(testDate);
        assertEquals(testDate, day.getDateOfSunriseSunset(), "Date should be set correctly");
    }

    @Test
    void testSetAndGetTimeOfSunrise() {
        Day day = new Day();
        LocalTime testTime = LocalTime.of(6, 0);
        day.setTimeOfSunrise(testTime);
        assertEquals(testTime, day.getTimeOfSunrise(), "Sunrise time should be set correctly");
    }

    @Test
    void testSetAndGetTimeOfSunset() {
        Day day = new Day();
        LocalTime testTime = LocalTime.of(18, 0);
        day.setTimeOfSunset(testTime);
        assertEquals(testTime, day.getTimeOfSunset(), "Sunset time should be set correctly");
    }

    @Test
    void testSetAndGetWeatherConditions() {
        Day day = new Day();
        day.setWeatherConditions("Sunny");
        assertEquals("Sunny", day.getWeatherConditions(), "Weather conditions should be set correctly");
    }

    @Test
    void testSetAndGetCountry() {
        Day day = new Day();
        Country mockCountry = mock(Country.class);
        day.setCountry(mockCountry);
        assertEquals(mockCountry, day.getCountry(), "Country should be set correctly");
    }

    @Test
    void testSetAndGetCoordinates() {
        Day day = new Day();
        day.setCoordinates("12.34, 56.78");
        assertEquals("12.34, 56.78", day.getCoordinates(), "Coordinates should be set correctly");
    }
}
