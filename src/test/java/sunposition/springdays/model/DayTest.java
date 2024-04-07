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

    @Test
    void testToString() {
        Day day = new Day();
        day.setId(1L);
        day.setLocation("Test Location");
        day.setCoordinates("12.34, 56.78");
        day.setDateOfSunriseSunset(LocalDate.of(2024, 4, 7));
        day.setTimeOfSunrise(LocalTime.of(6, 0));
        day.setTimeOfSunset(LocalTime.of(18, 0));
        day.setWeatherConditions("Sunny");

        String expectedToString = "Day{"
                + "id=1"
                + ", location='Test Location'"
                + ", coordinates='12.34, 56.78'"
                + ", dateOfSunriseSunset=2024-04-07"
                + ", timeOfSunrise=06:00"
                + ", timeOfSunset=18:00"
                + ", weatherConditions='Sunny'"
                + '}';

        assertEquals(expectedToString, day.toString(), "toString should return the correct string representation");
    }

    @Test
    void testEqualsAndHashCode() {
        Day day1 = new Day();
        day1.setId(1L);
        day1.setLocation("Test Location");
        day1.setCoordinates("12.34, 56.78");
        day1.setDateOfSunriseSunset(LocalDate.of(2024, 4, 7));
        day1.setTimeOfSunrise(LocalTime.of(6, 0));
        day1.setTimeOfSunset(LocalTime.of(18, 0));
        day1.setWeatherConditions("Sunny");

        Day day2 = new Day();
        day2.setId(1L);
        day2.setLocation("Test Location");
        day2.setCoordinates("12.34, 56.78");
        day2.setDateOfSunriseSunset(LocalDate.of(2024, 4, 7));
        day2.setTimeOfSunrise(LocalTime.of(6, 0));
        day2.setTimeOfSunset(LocalTime.of(18, 0));
        day2.setWeatherConditions("Sunny");

        assertEquals(day1, day2, "Two days with the same properties should be equal");
        assertEquals(day1.hashCode(), day2.hashCode(), "Two days with the same properties should have the same hash code");
    }

    @Test
    void testEqualsWithDifferentObjects() {
        Day day1 = new Day();
        day1.setId(1L);
        day1.setLocation("Location1");
        day1.setCoordinates("12.34, 56.78");
        day1.setDateOfSunriseSunset(LocalDate.of(2024, 4, 7));
        day1.setTimeOfSunrise(LocalTime.of(6, 0));
        day1.setTimeOfSunset(LocalTime.of(18, 0));
        day1.setWeatherConditions("Sunny");

        Day day2 = new Day();
        day2.setId(2L);
        day2.setLocation("Location2");
        day2.setCoordinates("12.35, 56.79");
        day2.setDateOfSunriseSunset(LocalDate.of(2024, 4, 8));
        day2.setTimeOfSunrise(LocalTime.of(7, 0));
        day2.setTimeOfSunset(LocalTime.of(19, 0));
        day2.setWeatherConditions("Cloudy");

        assertNotEquals(day1, day2, "Days with different properties should not be equal");
    }

    @Test
    void testHashCodeWithDifferentObjects() {
        Day day1 = new Day();
        day1.setId(1L);
        day1.setLocation("Location1");
        day1.setCoordinates("12.34, 56.78");
        day1.setDateOfSunriseSunset(LocalDate.of(2024, 4, 7));
        day1.setTimeOfSunrise(LocalTime.of(6, 0));
        day1.setTimeOfSunset(LocalTime.of(18, 0));
        day1.setWeatherConditions("Sunny");

        Day day2 = new Day();
        day2.setId(2L);
        day2.setLocation("Location2");
        day2.setCoordinates("12.35, 56.79");
        day2.setDateOfSunriseSunset(LocalDate.of(2024, 4, 8));
        day2.setTimeOfSunrise(LocalTime.of(7, 0));
        day2.setTimeOfSunset(LocalTime.of(19, 0));
        day2.setWeatherConditions("Cloudy");

        assertNotEquals(day1.hashCode(), day2.hashCode(), "Days with different properties should have different hash codes");
    }

    @Test
    void testEqualsWithNull() {
        Day day = new Day();
        assertNotEquals(null, day, "Day object should not be equal to null");
    }

    @Test
    void testEqualsWithDifferentClass() {
        Day day = new Day();
        String differentClassObject = "Different Class Object";
        assertNotEquals(day, differentClassObject, "Day object should not be equal to object of different class");
    }

    @Test
    void testEqualsWithSameObject() {
        Day day = new Day();
        assertEquals(day, day, "Day object should be equal to itself");
    }


}
