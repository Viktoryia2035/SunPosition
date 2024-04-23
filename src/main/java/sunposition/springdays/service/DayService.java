package sunposition.springdays.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springdays.cache.DataCache;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.mapper.DayMapper;
import sunposition.springdays.model.Country;
import sunposition.springdays.model.Day;
import sunposition.springdays.repository.InMemoryDayDAO;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class DayService {
    private final InMemoryDayDAO repository;
    private CountryService countryService;
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

    public Day saveSunriseSunset(final Day day) {
        if (day.getCountry() != null && day.getCountry().getName() != null) {
            Country country = countryService.
                    findByName(day.getCountry().
                            getName()).
                    orElseThrow(() -> new HttpErrorExceptions.
                            CustomNotFoundException("Country not found"));
            day.setCountry(country);
        }
        return repository.save(day);
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

    public DayDto findDayById(final Long id) {
        try {
            String idAsString = String.valueOf(id);
            Object cachedObject = dayCache.get(idAsString);
            if (cachedObject instanceof DayDto dayDto) {
                return dayDto;
            }
            Optional<Day> optionalDay = repository.findById(id);
            if (optionalDay.isEmpty()) {
                throw new HttpErrorExceptions.
                        CustomNotFoundException(MESSAGE_OF_DAY);
            }
            Day day = optionalDay.get();
            DayDto dayDto = DayMapper.toDto(day);
            dayCache.put(idAsString, dayDto);
            return dayDto;
        } catch (Exception e) {
            throw new HttpErrorExceptions.
                    CustomInternalServerErrorException(
                            "An error occurred while fetching the country", e);
        }
    }

    public void updateDay(final Long id, final DayDto dayDto) {
        Day existingDay = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Day with ID " + id + " not found"));
        existingDay.setLocation(dayDto.getLocation());
        existingDay.setCoordinates(dayDto.getCoordinates());
        existingDay.setDateOfSunriseSunset(dayDto.getDateOfSunriseSunset());
        existingDay.setTimeOfSunrise(dayDto.getTimeOfSunrise());
        existingDay.setTimeOfSunset(dayDto.getTimeOfSunset());
        existingDay.setWeatherConditions(dayDto.getWeatherConditions());
        repository.save(existingDay);
        dayCache.remove(String.valueOf(id));
        dayCache.clear();
    }
}
