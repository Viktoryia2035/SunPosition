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
}
