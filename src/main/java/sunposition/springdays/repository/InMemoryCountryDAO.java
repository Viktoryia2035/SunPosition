package sunposition.springdays.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import sunposition.springdays.model.Country;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class InMemoryCountryDAO {

    private final SessionFactory sessionFactory;

    public Optional<Country> findByName(String name) {
        return sessionFactory.getCurrentSession().createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    public List<Country> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Country> query = session.createQuery("SELECT c FROM Country c", Country.class);
        return query.list();
    }

    public void save(final Country country) {
        sessionFactory.inTransaction(session -> session.persist(country));
    }

    public List<Country> saveAll(List<Country> countries) {
        Session session = sessionFactory.getCurrentSession();
        countries.forEach(session::saveOrUpdate);
        return countries;
    }

    public Optional<Country> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Country.class, id));
    }

    public void deleteAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Country");
        query.executeUpdate();
    }

    public void delete(Country country) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(country);
    }

    public Country saveAndFlush(Country country) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(country);
        session.flush();
        return country;
    }
}
