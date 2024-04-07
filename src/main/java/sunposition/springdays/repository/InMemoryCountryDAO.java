package sunposition.springdays.repository;

import org.springframework.stereotype.Repository;
import sunposition.springdays.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
}
