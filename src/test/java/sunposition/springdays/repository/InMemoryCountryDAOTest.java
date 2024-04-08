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
    void saveAndFlush() {
        Country country = new Country();
        country.setName("TestCountry");

        Country savedCountry = inMemoryCountryDAO.saveAndFlush(country);

        assertNotNull(savedCountry);
        assertEquals(country, savedCountry);
        verify(entityManager).persist(country);
        verify(entityManager).flush();
    }

    @Test
    void saveAll() {
        Country country1 = new Country();
        country1.setName("Country1");
        Country country2 = new Country();
        country2.setName("Country2");
        List<Country> countries = Arrays.asList(country1, country2);

        inMemoryCountryDAO.saveAll(countries);

        for (Country country : countries) {
            verify(entityManager).persist(country);
        }
        verify(entityManager).flush();
    }

    @Test
    void save() {
        Country country = new Country();
        country.setName("TestCountry");

        Country savedCountry = inMemoryCountryDAO.save(country);

        assertNotNull(savedCountry);
        assertEquals(country, savedCountry);
        verify(entityManager).persist(country);
    }

    @Test
    void findById() {
        Long id = 1L;
        Country country = new Country();
        country.setId(id);

        when(entityManager.find(Country.class, id)).thenReturn(country);

        Optional<Country> result = inMemoryCountryDAO.findById(id);

        assertTrue(result.isPresent());
        assertEquals(country, result.get());
        verify(entityManager).find(Country.class, id);
    }

    @Test
    void deleteAll() {
        Country country1 = new Country();
        country1.setName("Country1");
        Country country2 = new Country();
        country2.setName("Country2");
        List<Country> countries = Arrays.asList(country1, country2);

        for (Country country : countries) {
            when(entityManager.contains(country)).thenReturn(true);
        }

        inMemoryCountryDAO.deleteAll(countries);

        for (Country country : countries) {
            verify(entityManager).remove(country);
        }
    }

    @Test
    void delete() {
        Country country = new Country();
        country.setName("TestCountry");

        when(entityManager.contains(country)).thenReturn(true);

        inMemoryCountryDAO.delete(country);

        verify(entityManager).remove(country);
    }

    @Test
    void findByName_WhenCountryExists_ReturnsOptionalWithCountry() {
        String name = "TestCountry";
        Country country = new Country();
        country.setName(name);

        when(entityManager.createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("name", name)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(country);

        Optional<Country> result = inMemoryCountryDAO.findByName(name);

        assertTrue(result.isPresent());
        assertEquals(country, result.get());
        verify(entityManager).createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class);
        verify(typedQuery).setParameter("name", name);
        verify(typedQuery).getSingleResult();
    }

    @Test
    void findByName_WhenCountryDoesNotExist_ReturnsEmptyOptional() {
        String name = "TestCountry";

        when(entityManager.createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("name", name)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(new RuntimeException());

        Optional<Country> result = inMemoryCountryDAO.findByName(name);

        assertFalse(result.isPresent());
        verify(entityManager).createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class);
        verify(typedQuery).setParameter("name", name);
        verify(typedQuery).getSingleResult();
    }

    @Test
    void findAll_ReturnsListOfCountries() {
        Country country1 = new Country();
        country1.setName("Country1");
        Country country2 = new Country();
        country2.setName("Country2");
        List<Country> countries = Arrays.asList(country1, country2);

        when(entityManager.createQuery("SELECT c FROM Country c", Country.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(countries);

        List<Country> result = inMemoryCountryDAO.findAll();

        assertEquals(countries, result);
        verify(entityManager).createQuery("SELECT c FROM Country c", Country.class);
        verify(typedQuery).getResultList();
    }
}