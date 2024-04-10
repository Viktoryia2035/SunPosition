package sunposition.springdays.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import sunposition.springdays.model.Day;

import java.util.List;

@AllArgsConstructor
@Repository
public class InMemoryDayDAO {

    private final SessionFactory sessionFactory;

    public Day findByLocation(String location) {
        Session session = sessionFactory.getCurrentSession();
        Query<Day> query = session.createQuery("SELECT d FROM Day d WHERE d.location = :location", Day.class);
        query.setParameter("location", location);
        return query.uniqueResult();
    }

    public List<Day> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Day> query = session.createQuery("SELECT d FROM Day d", Day.class);
        return query.list();
    }

    public Day save(Day day) {
        sessionFactory.inTransaction(session -> session.persist(day));
        return day;
    }

    public void delete(Day day) {
        sessionFactory.inTransaction(session -> session.delete(day));
    }

    public Day findByCoordinates(String coordinates) {
        Session session = sessionFactory.getCurrentSession();
        Query<Day> query = session.createQuery("SELECT d FROM Day d WHERE d.coordinates = :coordinates", Day.class);
        query.setParameter("coordinates", coordinates);
        return query.uniqueResult();
    }

    public List<Day> findByCountryNameAndWeatherConditions(String countryName, String weatherConditions) {
        Session session = sessionFactory.getCurrentSession();
        Query<Day> query = session.createQuery(
                "SELECT d FROM Day d JOIN d.country c WHERE c.name = :countryName AND d.weatherConditions = :weatherConditions",
                Day.class);
        query.setParameter("countryName", countryName);
        query.setParameter("weatherConditions", weatherConditions);
        return query.list();
    }

    public List<Day> saveAll(List<Day> days) {
        Session session = sessionFactory.getCurrentSession();
        days.forEach(session::saveOrUpdate);
        return days;
    }

    public void deleteAll(List<Day> days) {
        Session session = sessionFactory.getCurrentSession();
        days.forEach(session::delete);
    }
}