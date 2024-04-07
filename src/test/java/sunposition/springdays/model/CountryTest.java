package sunposition.springdays.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CountryTest {

    @Test
    void testCountryCreation() {
        Country country = new Country();
        assertNotNull(country, "Country object should not be null");
    }

    @Test
    void testSetAndGetName() {
        Country country = new Country();
        country.setName("Test Country");
        assertEquals("Test Country", country.getName(), "Name should be set correctly");
    }

    @Test
    void testAddAndRemoveDay() {
        Country country = new Country();
        Day day = mock(Day.class);
        when(day.getCountry()).thenReturn(country);

        country.getDays().add(day);
        assertEquals(1, country.getDays().size(), "Day should be added to the list");

        country.getDays().remove(day);
        assertEquals(0, country.getDays().size(), "Day should be removed from the list");
    }

    @Test
    void testSetAndGetCapital() {
        Country country = new Country();
        country.setCapital("Test Capital");
        assertEquals("Test Capital", country.getCapital(), "Capital should be set correctly");
    }

    @Test
    void testSetAndGetPopulation() {
        Country country = new Country();
        country.setPopulation(1000L);
        assertEquals(1000L, country.getPopulation(), "Population should be set correctly");
    }

    @Test
    void testSetAndGetLanguage() {
        Country country = new Country();
        country.setLanguage("Test Language");
        assertEquals("Test Language", country.getLanguage(), "Language should be set correctly");
    }

    @Test
    void testAddDays() {
        Country country = new Country();
        Day day1 = mock(Day.class);
        Day day2 = mock(Day.class);
        when(day1.getCountry()).thenReturn(country);
        when(day2.getCountry()).thenReturn(country);

        country.getDays().add(day1);
        country.getDays().add(day2);
        assertEquals(2, country.getDays().size(), "Two days should be added to the list");
    }

    @Test
    void testClearDays() {
        Country country = new Country();
        Day day = mock(Day.class);
        when(day.getCountry()).thenReturn(country);

        country.getDays().add(day);
        country.getDays().clear();
        assertEquals(0, country.getDays().size(), "Days list should be cleared");
    }

    @Test
    void testEqualsAndHashCode() {
        Country country1 = new Country();
        country1.setName("Test Country");
        country1.setCapital("Test Capital");
        country1.setPopulation(1000L);
        country1.setLanguage("Test Language");

        Country country2 = new Country();
        country2.setName("Test Country");
        country2.setCapital("Test Capital");
        country2.setPopulation(1000L);
        country2.setLanguage("Test Language");

        assertEquals(country1, country2, "Two countries with the same properties should be equal");
        assertEquals(country1.hashCode(), country2.hashCode(), "Two countries with the same properties should have the same hash code");
    }

    @Test
    void testEqualsWithDifferentObjects() {
        Country country1 = new Country();
        country1.setName("Country1");
        country1.setCapital("Capital1");
        country1.setPopulation(1000L);
        country1.setLanguage("Language1");

        Country country2 = new Country();
        country2.setName("Country2");
        country2.setCapital("Capital2");
        country2.setPopulation(2000L);
        country2.setLanguage("Language2");

        assertNotEquals(country1, country2, "Countries with different properties should not be equal");
    }

    @Test
    void testHashCodeWithDifferentObjects() {
        Country country1 = new Country();
        country1.setName("Country1");
        country1.setCapital("Capital1");
        country1.setPopulation(1000L);
        country1.setLanguage("Language1");

        Country country2 = new Country();
        country2.setName("Country2");
        country2.setCapital("Capital2");
        country2.setPopulation(2000L);
        country2.setLanguage("Language2");

        assertNotEquals(country1.hashCode(), country2.hashCode(), "Countries with different properties should have different hash codes");
    }

    @Test
    void testEqualsWithNull() {
        Country country = new Country();
        assertNotEquals(null, country, "Country object should not be equal to null");
    }

    @Test
    void testEqualsWithDifferentClass() {
        Country country = new Country();
        String differentClassObject = "Different Class Object";
        assertNotEquals(country, differentClassObject, "Country object should not be equal to object of different class");
    }

    @Test
    void testEqualsWithSameObject() {
        Country country = new Country();
        assertEquals(country, country, "Country object should be equal to itself");
    }


}
