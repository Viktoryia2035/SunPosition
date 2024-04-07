package sunposition.springdays.mapper;

import sunposition.springdays.dto.CountryDto;
import sunposition.springdays.model.Country;

public final class CountryMapper {

    private CountryMapper() {
    }

    public static CountryDto toDto(final Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.getId());
        countryDto.setName(country.getName());
        countryDto.setCapital(country.getCapital());
        countryDto.setPopulation(country.getPopulation());
        countryDto.setLanguage(country.getLanguage());
        return countryDto;
    }

    public static Country toEntity(final CountryDto countryDto) {
        Country country = new Country();
        country.setId(countryDto.getId());
        country.setName(countryDto.getName());
        country.setCapital(countryDto.getCapital());
        country.setPopulation(countryDto.getPopulation());
        country.setLanguage(countryDto.getLanguage());
        return country;
    }
}
