package sunposition.springdays.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sunposition.springdays.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryCountryDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Country> typedQuery;

    @InjectMocks
    private InMemoryCountryDAO inMemoryCountryDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByName() {
        String name = "TestCountry";
        Country country = new Country();
        country.setName(name);

        when(entityManager.createQuery(anyString(), eq(Country.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(country);

        Optional<Country> result = inMemoryCountryDAO.findByName(name);

        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());
        verify(entityManager).createQuery(anyString(), eq(Country.class));
        verify(typedQuery).setParameter(anyString(), any());
        verify(typedQuery).getSingleResult();
    }

    @Test
    void findAll() {
        Country country1 = new Country();
        country1.setName("Country1");
        Country country2 = new Country();
        country2.setName("Country2");
        List<Country> countries = Arrays.asList(country1, country2);

        when(entityManager.createQuery(anyString(), eq(Country.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(countries);

        List<Country> result = inMemoryCountryDAO.findAll();

        assertEquals(2, result.size());
        assertEquals("Country1", result.get(0).getName());
        assertEquals("Country2", result.get(1).getName());
        verify(entityManager).createQuery(anyString(), eq(Country.class));
        verify(typedQuery).getResultList();
    }


}
