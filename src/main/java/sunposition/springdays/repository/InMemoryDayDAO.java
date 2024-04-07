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
}
