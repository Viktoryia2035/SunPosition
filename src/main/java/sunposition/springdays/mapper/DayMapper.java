package sunposition.springdays.mapper;

import sunposition.springdays.dto.DayDto;
import sunposition.springdays.model.Day;

public final class DayMapper {

    private DayMapper() {
    }

    public static DayDto toDto(final Day day) {
        if (day == null) {
            return null;
        }
        DayDto dayDto = new DayDto();
        dayDto.setId(day.getId());
        dayDto.setLocation(day.getLocation());
        dayDto.setCoordinates(day.getCoordinates());
        dayDto.setDateOfSunriseSunset(day.getDateOfSunriseSunset());
        dayDto.setTimeOfSunrise(day.getTimeOfSunrise());
        dayDto.setTimeOfSunset(day.getTimeOfSunset());
        dayDto.setWeatherConditions(day.getWeatherConditions());
        return dayDto;
    }

    public static Day toEntity(final DayDto dayDto) {
        if (dayDto == null) {
            return null;
        }
        Day day = new Day();
        day.setId(dayDto.getId());
        day.setLocation(dayDto.getLocation());
        day.setCoordinates(dayDto.getCoordinates());
        day.setDateOfSunriseSunset(dayDto.getDateOfSunriseSunset());
        day.setTimeOfSunrise(dayDto.getTimeOfSunrise());
        day.setTimeOfSunset(dayDto.getTimeOfSunset());
        day.setWeatherConditions(dayDto.getWeatherConditions());
        return day;
    }
}
