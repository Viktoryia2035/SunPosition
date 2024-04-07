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
}
