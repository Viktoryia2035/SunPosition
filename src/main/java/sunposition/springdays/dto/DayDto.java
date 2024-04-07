package sunposition.springdays.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayDto {
    @JsonIgnore
    private Long id;

    private String location;
    private String coordinates;

    @NotNull(message = "Date of sunrise/sunset cannot be null")
    @JsonProperty("dateOfSunriseSunset")
    private LocalDate dateOfSunriseSunset;

    private LocalTime timeOfSunrise;
    private LocalTime timeOfSunset;
    private String weatherConditions;

    public LocalDate getDate() {
        return dateOfSunriseSunset;
    }

    public void setDateOfSunriseSunset(LocalDate dateOfSunriseSunset) {
        if (dateOfSunriseSunset == null) {
            throw new IllegalArgumentException("Date of sunrise/sunset cannot be null");
        }
        this.dateOfSunriseSunset = dateOfSunriseSunset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayDto dayDto = (DayDto) o;
        return Objects.equals(id, dayDto.id) &&
                Objects.equals(location, dayDto.location) &&
                Objects.equals(coordinates, dayDto.coordinates) &&
                Objects.equals(dateOfSunriseSunset, dayDto.dateOfSunriseSunset) &&
                Objects.equals(timeOfSunrise, dayDto.timeOfSunrise) &&
                Objects.equals(timeOfSunset, dayDto.timeOfSunset) &&
                Objects.equals(weatherConditions, dayDto.weatherConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, coordinates, dateOfSunriseSunset, timeOfSunrise, timeOfSunset, weatherConditions);
    }

}