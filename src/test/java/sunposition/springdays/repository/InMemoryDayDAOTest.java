package sunposition.springdays.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sunposition.springdays.model.Day;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InMemoryDayDAOTest {

    @Autowired
    private InMemoryDayDAO dayDAO;

    @BeforeEach
    void setUp() {
        // Assuming the DAO methods for saving entities are correctly implemented
        Day day1 = new Day();
        day1.setLocation("Location 1");
        day1.setCoordinates("Coordinates 1");
        day1.setWeatherConditions("Sunny");
        dayDAO.save(day1);

        Day day2 = new Day();
        day2.setLocation("Location 2");
        day2.setCoordinates("Coordinates 2");
        day2.setWeatherConditions("Cloudy");
        dayDAO.save(day2);
    }

    @Test
    void testFindAll_ReturnsAllDays() {
        // Act
        List<Day> result = dayDAO.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testSave_SavesDay() {
        // Arrange
        Day day = new Day();
        day.setLocation("Location 3");
        day.setCoordinates("Coordinates 3");
        day.setWeatherConditions("Rainy");

        // Act
        Day result = dayDAO.save(day);

        // Assert
        assertNotNull(result.getId());
        assertEquals(day.getLocation(), result.getLocation());
    }

    @Test
    void testFindByLocation_ExistingLocation_ReturnsDay() {
        // Act
        Day result = dayDAO.findByLocation("Location 1");

        // Assert
        assertNotNull(result, "Expected a Day object but got null");
        assertEquals("Location 1", result.getLocation(), "Location does not match");
    }

    @Test
    void testDelete_DeletesDay() {
        // Arrange
        Day day = dayDAO.findByLocation("Location 1");
        assertNotNull(day, "Day to delete not found");

        // Act
        dayDAO.delete(day);
        List<Day> result = dayDAO.findAll();

        // Assert
        assertEquals(1, result.size(), "Expected only one Day object after deletion");
    }


    @Test
    void testFindByCoordinates_ExistingCoordinates_ReturnsDay() {
        // Act
        Day result = dayDAO.findByCoordinates("Coordinates 1");

        // Assert
        assertNotNull(result);
        assertEquals("Coordinates 1", result.getCoordinates());
    }

    @Test
    void testFindByCountryNameAndWeatherConditions_ReturnsDays() {
        // Assuming the DAO methods for querying entities are correctly implemented
        // This test assumes you have a country with the name "Country 1" and weather conditions "Sunny"
        // You would need to set up this relationship in your setUp method or in a separate test

        // Act
        List<Day> result = dayDAO.findByCountryNameAndWeatherConditions("Country 1", "Sunny");

        // Assert
        assertFalse(result.isEmpty());
        // Additional assertions based on your expected data
    }
}
