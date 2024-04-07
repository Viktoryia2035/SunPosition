package sunposition.springdays.mapper;

import org.junit.jupiter.api.Test;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.model.Day;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DayMapperTest {

    @Test
    void testToDto() {
        Day day = new Day();
        day.setId(1L);
        day.setLocation("Test Location");
        day.setCoordinates("Test Coordinates");
        day.setDateOfSunriseSunset(LocalDate.parse("2023-04-01"));
        day.setTimeOfSunrise(LocalTime.parse("06:00"));
        day.setTimeOfSunset(LocalTime.parse("18:00"));
        day.setWeatherConditions("Test Weather Conditions");

        DayDto dayDto = DayMapper.toDto(day);

        assertEquals(day.getId(), dayDto.getId());
        assertEquals(day.getLocation(), dayDto.getLocation());
        assertEquals(day.getCoordinates(), dayDto.getCoordinates());
        assertEquals(day.getDateOfSunriseSunset(), dayDto.getDateOfSunriseSunset());
        assertEquals(day.getTimeOfSunrise(), dayDto.getTimeOfSunrise());
        assertEquals(day.getTimeOfSunset(), dayDto.getTimeOfSunset());
        assertEquals(day.getWeatherConditions(), dayDto.getWeatherConditions());
    }

    @Test
    void testToEntity() {
        DayDto dayDto = new DayDto();
        dayDto.setId(1L);
        dayDto.setLocation("Test Location");
        dayDto.setCoordinates("Test Coordinates");
        dayDto.setDateOfSunriseSunset(LocalDate.parse("2023-04-01"));
        dayDto.setTimeOfSunrise(LocalTime.parse("06:00"));
        dayDto.setTimeOfSunset(LocalTime.parse("18:00"));
        dayDto.setWeatherConditions("Test Weather Conditions");

        Day day = DayMapper.toEntity(dayDto);

        assertEquals(dayDto.getId(), day.getId());
        assertEquals(dayDto.getLocation(), day.getLocation());
        assertEquals(dayDto.getCoordinates(), day.getCoordinates());
        assertEquals(dayDto.getDateOfSunriseSunset(), day.getDateOfSunriseSunset());
        assertEquals(dayDto.getTimeOfSunrise(), day.getTimeOfSunrise());
        assertEquals(dayDto.getTimeOfSunset(), day.getTimeOfSunset());
        assertEquals(dayDto.getWeatherConditions(), day.getWeatherConditions());
    }

    @Test
    void testToDtoWithNullValues() {
        Day day = new Day();
        day.setId(null);
        day.setLocation(null);
        day.setCoordinates(null);
        day.setDateOfSunriseSunset(null);
        day.setTimeOfSunrise(null);
        day.setTimeOfSunset(null);
        day.setWeatherConditions(null);

        DayDto dayDto = DayMapper.toDto(day);

        assertNull(dayDto.getId());
        assertNull(dayDto.getLocation());
        assertNull(dayDto.getCoordinates());
        assertNull(dayDto.getDateOfSunriseSunset());
        assertNull(dayDto.getTimeOfSunrise());
        assertNull(dayDto.getTimeOfSunset());
        assertNull(dayDto.getWeatherConditions());
    }

    @Test
    void testToDtoAndToEntityWithDifferentStates() {
        Day day = new Day();
        day.setId(1L);
        day.setLocation("Test Location");
        day.setCoordinates("Test Coordinates");
        day.setDateOfSunriseSunset(LocalDate.parse("2023-04-01"));
        day.setTimeOfSunrise(LocalTime.parse("06:00"));
        day.setTimeOfSunset(LocalTime.parse("18:00"));
        day.setWeatherConditions("Test Weather Conditions");

        DayDto dayDto = DayMapper.toDto(day);

        dayDto.setLocation("Updated Location");
        dayDto.setTimeOfSunrise(LocalTime.parse("07:00"));

        Day updatedDay = DayMapper.toEntity(dayDto);

        assertEquals(day.getId(), updatedDay.getId());
        assertEquals(dayDto.getLocation(), updatedDay.getLocation());
        assertEquals(day.getCoordinates(), updatedDay.getCoordinates());
        assertEquals(day.getDateOfSunriseSunset(), updatedDay.getDateOfSunriseSunset());
        assertEquals(dayDto.getTimeOfSunrise(), updatedDay.getTimeOfSunrise());
        assertEquals(day.getTimeOfSunset(), updatedDay.getTimeOfSunset());
        assertEquals(day.getWeatherConditions(), updatedDay.getWeatherConditions());
    }

}

