package sunposition.springdays.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryDtoTest {

    @Test
    void testCountryDtoCreation() {
        CountryDto countryDto = new CountryDto();
        assertNotNull(countryDto, "CountryDto object should not be null");
    }

    @Test
    void testSetAndGetName() {
        CountryDto countryDto = new CountryDto();
        countryDto.setName("Test Country");
        assertEquals("Test Country", countryDto.getName(), "Name should be set correctly");
    }

    @Test
    void testSetAndGetId() {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(1L);
        assertEquals(1L, countryDto.getId(), "ID should be set correctly");
    }

    @Test
    void testClearDays() {
        CountryDto countryDto = new CountryDto();
        DayDto dayDto = new DayDto();
        countryDto.getDays().add(dayDto);
        countryDto.getDays().clear();
        assertEquals(0, countryDto.getDays().size(), "Days list should be cleared");
    }

    @Test
    void testEqualsAndHashCodeWithDifferentProperties() {
        CountryDto countryDto1 = new CountryDto("Country1");
        CountryDto countryDto2 = new CountryDto("Country2");
        assertNotEquals(countryDto1, countryDto2, "CountryDto objects with different names should not be equal");
        assertNotEquals(countryDto1.hashCode(), countryDto2.hashCode(), "CountryDto objects with different names should not have the same hash code");
    }

    @Test
    void testSetAndGetCapital() {
        CountryDto countryDto = new CountryDto();
        countryDto.setCapital("Test Capital");
        assertEquals("Test Capital", countryDto.getCapital(), "Capital should be set correctly");
    }

    @Test
    void testSetAndGetPopulation() {
        CountryDto countryDto = new CountryDto();
        countryDto.setPopulation(1000000L);
        assertEquals(1000000L, countryDto.getPopulation(), "Population should be set correctly");
    }

    @Test
    void testSetAndGetLanguage() {
        CountryDto countryDto = new CountryDto();
        countryDto.setLanguage("Test Language");
        assertEquals("Test Language", countryDto.getLanguage(), "Language should be set correctly");
    }

    @Test
    void testAddAndRemoveDayDto() {
        CountryDto countryDto = new CountryDto();
        DayDto dayDto = new DayDto();
        countryDto.getDays().add(dayDto);
        assertEquals(1, countryDto.getDays().size(), "DayDto should be added to the list");

        countryDto.getDays().remove(dayDto);
        assertEquals(0, countryDto.getDays().size(), "DayDto should be removed from the list");
    }

    @Test
    void testEqualsAndHashCode() {
        CountryDto countryDto1 = new CountryDto("Country1");
        CountryDto countryDto2 = new CountryDto("Country1");
        assertEquals(countryDto1, countryDto2, "CountryDto objects with the same name should be equal");
        assertEquals(countryDto1.hashCode(), countryDto2.hashCode(), "CountryDto objects with the same name should have the same hash code");
    }

    @Test
    void testEqualsWithNull() {
        CountryDto countryDto = new CountryDto("Country1");
        assertNotEquals(null, countryDto, "CountryDto object should not be equal to null");
    }

    @Test
    void testEqualsWithDifferentClass() {
        CountryDto countryDto = new CountryDto("Country1");
        String differentClassObject = "Different Class Object";
        assertNotEquals(countryDto, differentClassObject, "CountryDto object should not be equal to object of different class");
    }

    @Test
    void testEqualsWithSameObject() {
        CountryDto countryDto = new CountryDto("Country1");
        assertEquals(countryDto, countryDto, "CountryDto object should be equal to itself");
    }

    @Test
    void testEqualsWithDifferentId() {
        CountryDto countryDto1 = new CountryDto("Country1");
        countryDto1.setId(1L);
        CountryDto countryDto2 = new CountryDto("Country1");
        countryDto2.setId(2L);
        assertNotEquals(countryDto1, countryDto2, "CountryDto objects with different IDs should not be equal");
    }

    @Test
    void testEqualsWithDifferentName() {
        CountryDto countryDto1 = new CountryDto("Country1");
        CountryDto countryDto2 = new CountryDto("Country2");
        assertNotEquals(countryDto1, countryDto2, "CountryDto objects with different names should not be equal");
    }
}
