package sunposition.springdays.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springdays.cache.DataCache;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.mapper.DayMapper;
import sunposition.springdays.model.Day;
import sunposition.springdays.repository.InMemoryDayDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
        allDays.forEach(day -> {
            String cacheKey = LOCATION_PREFIX + day.getLocation();
            DayDto dayDto = DayMapper.toDto(day);
            dayCache.put(cacheKey, dayDto);
        });
        return allDays;
    }

    public Day saveSunriseSunset(final Day day, final String httpMethod) {
        if (!"POST".equalsIgnoreCase(httpMethod)) {
            throw new HttpErrorExceptions.
                    CustomMethodNotAllowedException(
                            "Method not allowed for the provided request."
            );
        }
        try {
            Day savedDay = repository.save(day);
            String cacheKey = LOCATION_PREFIX + savedDay.getLocation();
            DayDto dayDto = DayMapper.toDto(savedDay);
            dayCache.put(cacheKey, dayDto);
            dayCache.clear();
            return savedDay;
        } catch (RuntimeException e) {
            throw new HttpErrorExceptions.
                    CustomInternalServerErrorException(
                            "An error occurred while saving the day",
                    e);
        }
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

    public void deleteDayByLocation(final String location) {
        Day dayToDelete = repository.findByLocation(location);
        if (dayToDelete != null) {
            repository.delete(dayToDelete);
            dayCache.remove(COORDINATES_PREFIX + location);
            dayCache.clear();
        } else {
            throw new HttpErrorExceptions.
                    CustomNotFoundException(MESSAGE_OF_DAY);
        }
    }

    public Optional<Day> findById(final Long id) {
        Optional<Day> day = repository.findById(id);
        if (day == null) {
            throw new EntityNotFoundException(
                    "Agency with ID " + id + " not found");
        }
        return day;
    }

    public void updateDayById(Long id, DayDto dayDto) {
        // Fetch the existing Day entity by ID
        Day existingDay = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Day with ID " + id + " not found"));

        // Update the fields of the existing Day entity with the values from the DayDto
        existingDay.setLocation(dayDto.getLocation());
        existingDay.setCoordinates(dayDto.getCoordinates());
        existingDay.setDateOfSunriseSunset(dayDto.getDateOfSunriseSunset());
        existingDay.setTimeOfSunrise(dayDto.getTimeOfSunrise());
        existingDay.setTimeOfSunset(dayDto.getTimeOfSunset());
        existingDay.setWeatherConditions(dayDto.getWeatherConditions());

        // Save the updated Day entity back to the database
        repository.save(existingDay);
    }
}
