package sunposition.springdays.mapper;

import org.junit.jupiter.api.Test;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.model.Day;

import java.time.LocalDate;
import java.time.LocalTime;

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
}

