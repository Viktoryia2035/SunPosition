package sunposition.springdays.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springdays.cache.DataCache;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.mapper.DayMapper;
import sunposition.springdays.model.Day;
import sunposition.springdays.repository.InMemoryDayDAO;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DayService {
    private final InMemoryDayDAO repository;
    private final DataCache dayCache;

    public static final String MESSAGE_OF_DAY = "Sunrise/sunset not found";
    private static final String LOCATION_PREFIX = "location_";
    private static final String COORDINATES_PREFIX = "coordinates_";

    public List<Day> findAllSunriseSunset() {
        List<Day> allDays = repository.findAll();
        allDays.stream().forEach(day -> {
            String cacheKey = LOCATION_PREFIX + day.getLocation();
            DayDto dayDto = DayMapper.toDto(day);
            dayCache.put(cacheKey, dayDto);
        });
        return allDays;
    }


    public Day saveSunriseSunset(final Day day, final String httpMethod) {
        if (!"POST".equalsIgnoreCase(httpMethod)) {
            throw new HttpErrorExceptions.
                    CustomMethodNotAllowedException("Method not allowed "
                    + "for the provided request.");
        }

        Day savedDay = repository.save(day);
        String cacheKey = LOCATION_PREFIX + savedDay.getLocation();
        DayDto dayDto = DayMapper.toDto(savedDay);
        dayCache.put(cacheKey, dayDto);
        dayCache.clear();
        return savedDay;
    }

    public Day findByLocation(final String location) {
        if (location == null || location.isEmpty()) {
            throw new HttpErrorExceptions.
                    CustomBadRequestException("Location "
                    + "cannot be null or empty");
        }
        String cacheKey = LOCATION_PREFIX + location;
        Object cachedObject = dayCache.get(cacheKey);
        if (cachedObject instanceof DayDto cachedDay) {
            return DayMapper.toEntity(cachedDay);
        }
        Day day = repository.findByLocation(location);
        if (day == null) {
            throw new HttpErrorExceptions.
                    CustomNotFoundException(MESSAGE_OF_DAY);
        }
        DayDto dayDto = DayMapper.toDto(day);
        dayCache.put(cacheKey, dayDto);
        return day;
    }

    public Day findByCoordinates(final String coordinates) {
        if (coordinates == null || coordinates.isEmpty()) {
            throw new HttpErrorExceptions.
                    CustomBadRequestException(
                    "Coordinates parameter is missing or invalid");
        }
        try {
            String cacheKey = COORDINATES_PREFIX + coordinates;
            Object cachedObject = dayCache.get(cacheKey);
            if (cachedObject instanceof DayDto cachedDay) {
                return DayMapper.toEntity(cachedDay);
            }
            Day day = repository.findByCoordinates(coordinates);
            if (day == null) {
                throw new HttpErrorExceptions.
                        CustomNotFoundException(MESSAGE_OF_DAY);
            }
            DayDto dayDto = DayMapper.toDto(day);
            dayCache.put(cacheKey, dayDto);
            return day;
        } catch (Exception e) {
            throw new HttpErrorExceptions.
                    CustomInternalServerErrorException(
                    "An error occurred while finding the day", e);
        }
    }

    public void deleteDayByCoordinates(final String coordinates) {
        Day dayToDelete = repository.findByCoordinates(coordinates);
        if (dayToDelete != null) {
            repository.delete(dayToDelete);
            dayCache.remove(COORDINATES_PREFIX + coordinates);
            dayCache.clear();
        } else {
            throw new HttpErrorExceptions.
                    CustomNotFoundException(MESSAGE_OF_DAY);
        }
    }

    public Day updateSunriseSunset(
            final String location,
            final String coordinates,
            final LocalDate dateOfSunriseSunset) {
        Day existingDay = repository.findByLocation(location);
        if (existingDay != null) {
            existingDay.setCoordinates(coordinates);
            existingDay.setDateOfSunriseSunset(dateOfSunriseSunset);
            Day updatedDay = repository.save(existingDay);
            dayCache.remove(LOCATION_PREFIX + location);
            dayCache.put(
                    COORDINATES_PREFIX + coordinates,
                    DayMapper.toDto(updatedDay)
            );
            dayCache.put(
                    "date_" + dateOfSunriseSunset.toString(),
                    DayMapper.toDto(updatedDay)
            );
            dayCache.clear();
            return updatedDay;
        } else {
            throw new HttpErrorExceptions.
                    CustomNotFoundException(MESSAGE_OF_DAY);
        }
    }
}
