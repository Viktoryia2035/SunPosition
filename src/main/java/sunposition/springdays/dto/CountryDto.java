package sunposition.springdays.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CountryDto {
    @JsonIgnore
    private Long id;
    private String name;
    private String capital;
    private Long population;
    private String language;
    private List<DayDto> days;

    public CountryDto(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountryDto other = (CountryDto) o;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    public void setDays(final List<DayDto> newDays) {
        days = newDays;
    }

    public List<DayDto> getDays() {
        if (days == null) {
            days = new ArrayList<>();
        }
        return days;
    }
}
