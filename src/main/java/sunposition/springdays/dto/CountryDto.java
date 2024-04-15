package sunposition.springdays.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class CountryDto {
    @JsonIgnore
    private Long id;
    private String name;
    private String capital;
    private Long population;
    private String language;
    private List<DayDto> days;
}
