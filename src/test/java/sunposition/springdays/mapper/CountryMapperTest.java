package sunposition.springdays.mapper;

import org.junit.jupiter.api.Test;
import sunposition.springdays.dto.CountryDto;
import sunposition.springdays.model.Country;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryMapperTest {

    @Test
    void testToDto() {
        Country country = new Country();
        country.setId(1L);
        country.setName("Test Country");
        country.setCapital("Test Capital");
        country.setPopulation(100L);
        country.setLanguage("Test Language");

        CountryDto countryDto = CountryMapper.toDto(country);

        assertEquals(country.getId(), countryDto.getId());
        assertEquals(country.getName(), countryDto.getName());
        assertEquals(country.getCapital(), countryDto.getCapital());
        assertEquals(country.getPopulation(), countryDto.getPopulation());
        assertEquals(country.getLanguage(), countryDto.getLanguage());
    }

    @Test
    void testToEntity() {
        CountryDto countryDto = new CountryDto("Country1");
        countryDto.setId(1L);
        countryDto.setName("Test Country");
        countryDto.setCapital("Test Capital");
        countryDto.setPopulation(1000000L);
        countryDto.setLanguage("Test Language");

        Country country = CountryMapper.toEntity(countryDto);

        assertEquals(countryDto.getId(), country.getId());
        assertEquals(countryDto.getName(), country.getName());
        assertEquals(countryDto.getCapital(), country.getCapital());
        assertEquals(countryDto.getPopulation(), country.getPopulation());
        assertEquals(countryDto.getLanguage(), country.getLanguage());
    }

    @Test
    void testToDtoWithNullValues() {
        Country country = new Country();
        country.setId(null);
        country.setName(null);
        country.setCapital(null);
        country.setPopulation(null);
        country.setLanguage(null);

        CountryDto countryDto = CountryMapper.toDto(country);

        assertNull(countryDto.getId());
        assertNull(countryDto.getName());
        assertNull(countryDto.getCapital());
        assertNull(countryDto.getPopulation());
        assertNull(countryDto.getLanguage());
    }

    @Test
    void testToEntityWithNullValues() {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(null);
        countryDto.setName(null);
        countryDto.setCapital(null);
        countryDto.setPopulation(null);
        countryDto.setLanguage(null);

        Country country = CountryMapper.toEntity(countryDto);

        assertNull(country.getId());
        assertNull(country.getName());
        assertNull(country.getCapital());
        assertNull(country.getPopulation());
        assertNull(country.getLanguage());
    }

    @Test
    void testToDtoAndToEntityWithDifferentStates() {
        Country country = new Country();
        country.setId(1L);
        country.setName("Test Country");
        country.setCapital("Test Capital");
        country.setPopulation(1000000L);
        country.setLanguage("Test Language");

        CountryDto countryDto = CountryMapper.toDto(country);

        countryDto.setName("Updated Country");
        countryDto.setPopulation(2000000L);

        Country updatedCountry = CountryMapper.toEntity(countryDto);

        assertEquals(country.getId(), updatedCountry.getId());
        assertEquals(countryDto.getName(), updatedCountry.getName());
        assertEquals(country.getCapital(), updatedCountry.getCapital());
        assertEquals(countryDto.getPopulation(), updatedCountry.getPopulation());
        assertEquals(country.getLanguage(), updatedCountry.getLanguage());
    }

}
