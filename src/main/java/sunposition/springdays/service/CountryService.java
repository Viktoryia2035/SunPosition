package sunposition.springdays.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springdays.cache.DataCache;
import sunposition.springdays.dto.CountryDto;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.mapper.CountryMapper;
import sunposition.springdays.mapper.DayMapper;
import sunposition.springdays.model.Country;
import sunposition.springdays.model.Day;
import sunposition.springdays.repository.InMemoryCountryDAO;
import sunposition.springdays.repository.InMemoryDayDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CountryService {
    private InMemoryCountryDAO repositoryOfCountry;
    private InMemoryDayDAO repositoryOfDay;
    private DataCache countryCache;
    private DataCache dayCache;
    private static final String ERROR_OCCURRED_MESSAGE = "An error occurred ";

    public void setCountryCache(final DataCache newCountryCache) {
        this.countryCache = newCountryCache;
    }

    public void setDayCache(final DataCache newDayCache) {
        this.dayCache = newDayCache;
    }

    public static final String MESSAGE_OF_COUNTRY = "Country not found";
    public static final String MESSAGE_COUNTRY_ALREADY_EXISTS =
            "Country with the same name already exists";

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CountryService.class);

    public List<CountryDto> findAll() {
        CounterService.incrementRequestCount();
        int requestCount = CounterService.getRequestCount();
        LOGGER.info("Текущее количество запросов: {}", requestCount);
        try {
            Object cachedObject = countryCache.get("all");
            if (cachedObject instanceof List<?> list
                    && !list.isEmpty() && list.get(0) instanceof CountryDto) {
                return list.stream()
                        .map(CountryDto.class::cast)
                        .toList();
            }
            List<Country> countries = repositoryOfCountry.findAll();
            List<CountryDto> countryDtos = countries.stream()
                    .map(CountryMapper::toDto)
                    .toList();

            countryCache.put("all", countryDtos);
            return countryDtos;
        } catch (HttpErrorExceptions.CustomMethodNotAllowedException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpErrorExceptions.
                    CustomInternalServerErrorException(ERROR_OCCURRED_MESSAGE
                    + "while fetching countries", e);
        }

    }

    public CountryDto saveCountry(final CountryDto countryDto) {
        if (countryDto.getName() == null || countryDto.getName().isEmpty()) {
            throw new HttpErrorExceptions.
                    CustomBadRequestException("Country name cannot be empty");
        }
        try {
            Country country = CountryMapper.toEntity(countryDto);
            Country savedCountry = repositoryOfCountry.saveAndFlush(country);
            List<Day> days = Optional.ofNullable(countryDto.getDays())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(dayDto -> {
                        Day day = DayMapper.toEntity(dayDto);
                        day.setCountry(country);
                        return day;
                    })
                    .toList();

            country.setDays(days);
            List<DayDto> savedDaysDto = savedCountry.getDays().stream()
                    .map(DayMapper::toDto)
                    .toList();

            countryDto.setDays(savedDaysDto);
            countryCache.put(savedCountry.getName(),
                    CountryMapper.toDto(savedCountry));
            countryCache.clear();
            return countryDto;
        } catch (Exception e) {
            throw new HttpErrorExceptions.
                    CustomInternalServerErrorException(ERROR_OCCURRED_MESSAGE
                    + "while saving the country", e);
        }
    }

    public void bulkSaveDays(final List<CountryDto> countryDtoList) {
        try {
            List<Country> countriesToSave = new ArrayList<>();

            for (CountryDto countryDto : countryDtoList) {
                Optional<Country> existingCountry = repositoryOfCountry.
                        findByName(countryDto.getName());

                if (existingCountry.isPresent()) {
                    throw new HttpErrorExceptions.
                            CustomBadRequestException("Country already exists: "
                            + countryDto.getName());
                }

                Country country = CountryMapper.toEntity(countryDto);
                countriesToSave.add(country);

                List<Day> days = new ArrayList<>();

                for (DayDto dayDto : countryDto.getDays()) {
                    Day day = DayMapper.toEntity(dayDto);
                    day.setCountry(country);
                    days.add(day);
                }

                country.setDays(days);
            }

            repositoryOfCountry.saveAll(countriesToSave);

            countryCache.clear();
            dayCache.clear();
        } catch (Exception e) {
            throw new HttpErrorExceptions.CustomInternalServerErrorException(
                    ERROR_OCCURRED_MESSAGE + " while saving days", e);
        }
    }

    public CountryDto findByNameCountry(final String name) {
        try {
            Object cachedObject = countryCache.get(name);
            if (cachedObject instanceof CountryDto countryDto) {
                return countryDto;
            }
            Optional<Country> optionalCountry =
                    repositoryOfCountry.findByName(name);
            if (optionalCountry.isEmpty()) {
                throw new HttpErrorExceptions.
                        CustomNotFoundException(MESSAGE_OF_COUNTRY);
            }
            Country country = optionalCountry.get();
            CountryDto countryDto = CountryMapper.toDto(country);
            countryCache.put(name, countryDto);
            return countryDto;
        } catch (Exception e) {
            throw new HttpErrorExceptions.
                    CustomInternalServerErrorException(ERROR_OCCURRED_MESSAGE
                    + "while fetching the country", e);
        }
    }

    public void deleteCountryById(final Long id) {
        try {
            Country countryToDelete = repositoryOfCountry.findById(id)
                    .orElseThrow(() -> new HttpErrorExceptions.
                            CustomNotFoundException(MESSAGE_OF_COUNTRY));
            List<Day> daysToDelete = countryToDelete.getDays();
            if (daysToDelete != null && !daysToDelete.isEmpty()) {
                repositoryOfDay.deleteAll(daysToDelete);
            }
            repositoryOfCountry.delete(countryToDelete);
            countryCache.remove(countryToDelete.getName());
            countryCache.clear();
        } catch (HttpErrorExceptions.CustomNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpErrorExceptions.
                    CustomInternalServerErrorException(
                    "Error deleting a country by ID", e);
        }
    }

    public CountryDto updateCountryByName(
            final String name, final String newName) {
        try {
            if (newName == null || newName.isEmpty()) {
                throw new HttpErrorExceptions.
                        CustomBadRequestException("New country name "
                        + "cannot be empty");
            }

            Country existingCountry = repositoryOfCountry.findByName(name)
                    .orElseThrow(() -> new HttpErrorExceptions.
                            CustomNotFoundException(MESSAGE_OF_COUNTRY));

            if (repositoryOfCountry.findByName(newName).isPresent()) {
                throw new HttpErrorExceptions.
                        CustomBadRequestException(
                        MESSAGE_COUNTRY_ALREADY_EXISTS
                );
            }

            existingCountry.setName(newName);
            repositoryOfCountry.save(existingCountry);

            countryCache.remove(name);
            countryCache.put(newName, CountryMapper.toDto(existingCountry));
            countryCache.remove(name);
            countryCache.remove(newName);

            return CountryMapper.toDto(existingCountry);
        } catch (HttpErrorExceptions.CustomNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpErrorExceptions.
                    CustomInternalServerErrorException("Error when updating "
                    + "the country name", e);
        }
    }

    public List<DayDto> findByCountryNameAndWeatherConditions(
            final String countryName, final String weatherConditions) {
        try {
            String cacheKey = countryName + "_" + weatherConditions;
            Object cachedObject = dayCache.get(cacheKey);
            if (cachedObject instanceof List<?> list
                    && !list.isEmpty() && list.get(0) instanceof DayDto) {
                return (List<DayDto>) list;
            }
            List<Day> days = repositoryOfDay.
                    findByCountryNameAndWeatherConditions(
                            countryName, weatherConditions
                    );
            if (days == null || days.isEmpty()) {
                throw new HttpErrorExceptions.
                        CustomNotFoundException("Days not found "
                        + "for the given country and weather conditions");
            }
            List<DayDto> dayDtos = days.stream()
                    .map(DayMapper::toDto)
                    .toList();

            dayCache.put(cacheKey, dayDtos);
            return dayDtos;
        } catch (HttpErrorExceptions.CustomNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpErrorExceptions.
                    CustomInternalServerErrorException("Error when "
                    + "getting days by country and weather", e);
        }
    }
}
