package sunposition.springdays.repository;

import lombok.AllArgsConstructor;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import sunposition.springdays.model.Country;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class InMemoryCountryDAO {

    private final HibernateTemplate hibernateTemplate;

    public Optional<Country> findByName(String name) {
        List<Country> countries = hibernateTemplate.findByNamedParam("SELECT c FROM Country c WHERE c.name = :name", "name", name);
        if (countries.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(countries.get(0));
        }
    }

    public List<Country> findAll() {
        return hibernateTemplate.loadAll(Country.class);
    }

    public void save(final Country country) {
        hibernateTemplate.save(country);
    }

    public List<Country> saveAll(List<Country> countries) {
        hibernateTemplate.saveOrUpdateAll(countries);
        return countries;
    }

    public Optional<Country> findById(Long id) {
        return Optional.ofNullable(hibernateTemplate.get(Country.class, id));
    }

    public void deleteAll() {
        List<Country> countries = findAll();
        hibernateTemplate.deleteAll(countries);
    }

    public void delete(Country country) {
        hibernateTemplate.delete(country);
    }

    public Country saveAndFlush(Country country) {
        hibernateTemplate.saveOrUpdate(country);
        hibernateTemplate.flush();
        return country;
    }
}