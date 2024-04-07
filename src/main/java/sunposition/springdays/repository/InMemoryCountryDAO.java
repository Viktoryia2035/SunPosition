package sunposition.springdays.repository;

import org.springframework.stereotype.Repository;
import sunposition.springdays.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryCountryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Country> findByName(String name) {
        TypedQuery<Country> query = entityManager.createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class);
        query.setParameter("name", name);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Country> findAll() {
        TypedQuery<Country> query = entityManager.createQuery("SELECT c FROM Country c", Country.class);
        return query.getResultList();
    }

    public Country saveAndFlush(Country country) {
        entityManager.persist(country);
        entityManager.flush();
        return country;
    }

    public void saveAll(List<Country> countries) {
        for (Country country : countries) {
            entityManager.persist(country);
        }
        entityManager.flush();
    }

    public Country save(Country country) {
        entityManager.persist(country);
        return country;
    }

    public Optional<Country> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Country.class, id));
    }

    public void deleteAll(List<Country> countries) {
        for (Country country : countries) {
            entityManager.remove(entityManager.contains(country) ? country : entityManager.merge(country));
        }
    }

    public void delete(Country country) {
        entityManager.remove(entityManager.contains(country) ? country : entityManager.merge(country));
    }
}
