package sunposition.springdays.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sunposition.springdays.model.Day;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryDayDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Day> typedQuery;

    @InjectMocks
    private InMemoryDayDAO inMemoryDayDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Day day1 = new Day();
        day1.setLocation("Location1");
        Day day2 = new Day();
        day2.setLocation("Location2");
        List<Day> days = Arrays.asList(day1, day2);

        when(entityManager.createQuery(anyString(), eq(Day.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(days);

        List<Day> result = inMemoryDayDAO.findAll();

        assertEquals(2, result.size());
        assertEquals("Location1", result.get(0).getLocation());
        assertEquals("Location2", result.get(1).getLocation());
        verify(entityManager).createQuery(anyString(), eq(Day.class));
        verify(typedQuery).getResultList();
    }

    @Test
    void save() {
        Day day = new Day();

        Day savedDay = inMemoryDayDAO.save(day);

        assertEquals(day, savedDay);
        verify(entityManager).persist(day);
    }

    @Test
    void findByLocation_WhenDayExists_ReturnsDay() {
        String location = "TestLocation";
        Day day = new Day();
        day.setLocation(location);

        when(entityManager.createQuery("SELECT d FROM Day d WHERE d.location = :location", Day.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("location", location)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(day);

        Day result = inMemoryDayDAO.findByLocation(location);

        assertEquals(day, result);
        verify(entityManager).createQuery("SELECT d FROM Day d WHERE d.location = :location", Day.class);
        verify(typedQuery).setParameter("location", location);
        verify(typedQuery).getSingleResult();
    }

    @Test
    void findByLocation_WhenDayDoesNotExist_ReturnsNull() {
        String location = "TestLocation";

        when(entityManager.createQuery("SELECT d FROM Day d WHERE d.location = :location", Day.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("location", location)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(new RuntimeException());

        Day result = inMemoryDayDAO.findByLocation(location);

        assertNull(result);
        verify(entityManager).createQuery("SELECT d FROM Day d WHERE d.location = :location", Day.class);
        verify(typedQuery).setParameter("location", location);
        verify(typedQuery).getSingleResult();
    }

    @Test
    void findByCoordinates_WhenDayExists_ReturnsDay() {
        String coordinates = "TestCoordinates";
        Day day = new Day();
        day.setCoordinates(coordinates);

        when(entityManager.createQuery("SELECT d FROM Day d WHERE d.coordinates = :coordinates", Day.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("coordinates", coordinates)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(day);

        Day result = inMemoryDayDAO.findByCoordinates(coordinates);

        assertEquals(day, result);
        verify(entityManager).createQuery("SELECT d FROM Day d WHERE d.coordinates = :coordinates", Day.class);
        verify(typedQuery).setParameter("coordinates", coordinates);
        verify(typedQuery).getSingleResult();
    }

    @Test
    void findByCoordinates_WhenDayDoesNotExist_ReturnsNull() {
        String coordinates = "TestCoordinates";

        when(entityManager.createQuery("SELECT d FROM Day d WHERE d.coordinates = :coordinates", Day.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("coordinates", coordinates)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(new RuntimeException());

        Day result = inMemoryDayDAO.findByCoordinates(coordinates);

        assertNull(result);
        verify(entityManager).createQuery("SELECT d FROM Day d WHERE d.coordinates = :coordinates", Day.class);
        verify(typedQuery).setParameter("coordinates", coordinates);
        verify(typedQuery).getSingleResult();
    }
}
