package sunposition.springdays.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
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
}
