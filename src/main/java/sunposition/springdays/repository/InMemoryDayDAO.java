package sunposition.springdays.repository;

import org.springframework.stereotype.Repository;
import sunposition.springdays.model.Day;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InMemoryDayDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Day> findAll() {
        TypedQuery<Day> query = entityManager.createQuery("SELECT d FROM Day d", Day.class);
        return query.getResultList();
    }

    public Day save(Day day) {
        entityManager.persist(day);
        return day;
    }

    public void delete(Day day) {
        entityManager.remove(entityManager.contains(day) ? day : entityManager.merge(day));
    }

    public Day findByLocation(String location) {
        TypedQuery<Day> query = entityManager.createQuery("SELECT d FROM Day d WHERE d.location = :location", Day.class);
        query.setParameter("location", location);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Day findByCoordinates(String coordinates) {
        TypedQuery<Day> query = entityManager.createQuery("SELECT d FROM Day d WHERE d.coordinates = :coordinates", Day.class);
        query.setParameter("coordinates", coordinates);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Day> findByCountryNameAndWeatherConditions(String countryName, String weatherConditions) {
        TypedQuery<Day> query = entityManager.createQuery("SELECT d FROM Day d JOIN d.country c WHERE c.name = :countryName AND d.weatherConditions = :weatherConditions", Day.class);
        query.setParameter("countryName", countryName);
        query.setParameter("weatherConditions", weatherConditions);
        return query.getResultList();
    }

    public void saveAll(List<Day> days) {
        for (Day day : days) {
            entityManager.persist(day);
        }
        entityManager.flush();
    }

    public void deleteAll(List<Day> days) {
        for (Day day : days) {
            entityManager.remove(entityManager.contains(day) ? day : entityManager.merge(day));
        }
    }
}
