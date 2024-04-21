package sunposition.springdays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sunposition.springdays.model.Day;

import java.util.List;


public interface InMemoryDayDAO extends JpaRepository<Day, Long> {

    Day findByLocation(String location);

    Day findByCoordinates(String coordinates);

    @Query("SELECT d FROM Day d JOIN d.country c WHERE c.name = :countryName "
            + "AND d.weatherConditions = :weatherConditions")
    List<Day> findByCountryNameAndWeatherConditions(
            @Param("countryName") String countryName,
            @Param("weatherConditions") String weatherConditions
    );

    Day deleteDayById(final Long id);

    Day findDayById(final Long id);
}
