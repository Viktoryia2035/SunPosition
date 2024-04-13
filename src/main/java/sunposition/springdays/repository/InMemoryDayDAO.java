package sunposition.springdays.repository;

import lombok.AllArgsConstructor;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import sunposition.springdays.model.Day;

import java.util.List;

@AllArgsConstructor
@Repository
public class InMemoryDayDAO {

    private final HibernateTemplate hibernateTemplate;

    public Day findByLocation(String location) {
        List<Day> days = hibernateTemplate.findByNamedParam("SELECT d FROM Day d WHERE d.location = :location", "location", location);
        if (days.isEmpty()) {
            return null;
        } else {
            return days.get(0);
        }
    }

    public List<Day> findAll() {
        return hibernateTemplate.loadAll(Day.class);
    }

    public Day save(Day day) {
        hibernateTemplate.save(day);
        return day;
    }

    public void delete(Day day) {
        hibernateTemplate.delete(day);
    }

    public Day findByCoordinates(String coordinates) {
        List<Day> days = hibernateTemplate.findByNamedParam("FROM Day d WHERE d.coordinates = :coordinates", "coordinates", coordinates);
        if (days.isEmpty()) {
            return null;
        } else {
            return days.get(0);
        }
    }

    public List<Day> findByCountryNameAndWeatherConditions(String countryName, String weatherConditions) {
        String queryString = "SELECT d FROM Day d JOIN d.country c WHERE c.name = :countryName AND d.weatherConditions = :weatherConditions";
        return hibernateTemplate.findByNamedParam(queryString, new String[]{"countryName", "weatherConditions"}, new Object[]{countryName, weatherConditions});
    }

    public List<Day> saveAll(List<Day> days) {
        hibernateTemplate.saveOrUpdateAll(days);
        return days;
    }

    public void deleteAll(List<Day> days) {
        hibernateTemplate.deleteAll(days);
    }
}