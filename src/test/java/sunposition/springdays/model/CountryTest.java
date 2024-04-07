package sunposition.springdays.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}
