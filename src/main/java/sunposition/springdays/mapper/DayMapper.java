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
        return new DayDto(
                day.getId(),
                day.getLocation(),
                day.getCoordinates(),
                day.getDateOfSunriseSunset(),
                day.getTimeOfSunrise(),
                day.getTimeOfSunset(),
                day.getWeatherConditions()
        );
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
