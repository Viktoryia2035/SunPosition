package sunposition.springdays.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sunposition.springdays.model.Country;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InMemoryCountryDAOTest {

    @Autowired
    private InMemoryCountryDAO countryDAO;

    @BeforeEach
    void setUp() {
        // Assuming the DAO methods for saving entities are correctly implemented
        Country country1 = new Country();
        country1.setName("Country 1");
        countryDAO.save(country1);

        Country country2 = new Country();
        country2.setName("Country 2");
        countryDAO.save(country2);
    }

    @Test
    void testFindByName_ExistingName_ReturnsCountry() {
        // Arrange
        String name = "Country 1";

        // Act
        Optional<Country> result = countryDAO.findByName(name);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());
    }

    @Test
    void testFindByName_NonExistingName_ReturnsEmpty() {
        // Arrange
        String name = "Country 3";

        // Act
        Optional<Country> result = countryDAO.findByName(name);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll_ReturnsAllCountries() {
        // Act
        List<Country> result = countryDAO.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testSaveAndFlush_SavesCountry() {
        // Arrange
        Country country = new Country();
        country.setName("Country 3");

        // Act
        Country result = countryDAO.saveAndFlush(country);

        // Assert
        assertNotNull(result.getId());
        assertEquals(country.getName(), result.getName());
    }

    @Test
    void testSaveAll_SavesAllCountries() {
        // Arrange
        List<Country> countries = Arrays.asList(
                new Country(),
                new Country()
        );

        // Act
        countryDAO.saveAll(countries);
        List<Country> result = countryDAO.findAll();

        // Assert
        assertEquals(4, result.size());
    }

    @Test
    void testSave_SavesCountry() {
        // Arrange
        Country country = new Country();
        country.setName("Country 6");

        // Act
        Country result = countryDAO.save(country);

        // Assert
        assertNotNull(result.getId());
        assertEquals(country.getName(), result.getName());
    }

    @Test
    void testFindById_ExistingId_ReturnsCountry() {
        // Arrange
        Country country = new Country();
        country.setName("Country 7");
        countryDAO.save(country);

        // Act
        Optional<Country> result = countryDAO.findById(country.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(country.getId(), result.get().getId());
    }

    @Test
    void testFindById_NonExistingId_ReturnsEmpty() {
        // Arrange
        long id = 100;

        // Act
        Optional<Country> result = countryDAO.findById(id);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteAll_DeletesAllCountries() {
        // Arrange
        List<Country> countries = countryDAO.findAll();

        // Act
        countryDAO.deleteAll(countries);
        List<Country> result = countryDAO.findAll();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testDelete_DeletesCountry() {
        // Arrange
        Country country = countryDAO.findByName("Country 1").orElse(null);
        assertNotNull(country);

        // Act
        countryDAO.delete(country);
        List<Country> result = countryDAO.findAll();

        // Assert
        assertEquals(1, result.size());
    }
}
