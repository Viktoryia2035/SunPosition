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
}