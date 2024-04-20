package sunposition.springdays.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sunposition.springdays.dto.CountryDto;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.service.CountryService;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/api/v2/country")
@Tag(name = "Country Controller", description = "API для работы со странами")
@AllArgsConstructor
public class CountryController {

    private final CountryService service;

    static final Logger LOGGER = LogManager.getLogger(CountryController.class);
    private static final String REDIRECT = "redirect:/api/v2/country";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_REDIRECT = "days/error";

    @Operation(method = "GET",
            summary = "Получить все страны",
            description = "Возвращает список всех стран")
    @GetMapping
    public String findAllCountry(final Model model) {
        LOGGER.info("Finding all countries");
        List<CountryDto> countries = service.findAll();
        LOGGER.info("Found {} countries", countries.size());
        model.addAttribute("countries", countries);
        return "countries";
    }

    @GetMapping("/saveCountry")
    public String createCountry(@ModelAttribute("country") CountryDto countryDto) {
        return "createCountry";
    }

    @Operation(method = "POST",
            summary = "Сохранить страну",
            description = "Сохраняет новую страну в базе данных")
    @PostMapping("/saveCountry")
    public String saveCountry(
            @Valid @ModelAttribute("country") final CountryDto countryDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> redirectAttributes.addFlashAttribute(ERROR_MESSAGE, error.getDefaultMessage()));
            return "createCountry";
        }
        try {
            LOGGER.info("Saving a country");
            service.saveCountry(countryDto);
            LOGGER.info("Country saved successfully");
            redirectAttributes.addFlashAttribute("successMessage", "Country saved successfully");
            return REDIRECT;
        } catch (RuntimeException e) {
            LOGGER.error("Error saving country", e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Error saving country: " + e.getMessage());
            return "redirect:/createCountry";
        }
    }

    @Operation(method = "POST",
            summary = "Массовое сохранение дней",
            description = "Сохраняет множество дней для страны")
    @PostMapping("/bulkSaveDays")
    public ResponseEntity<String> bulkSaveDays(
            @Valid @RequestBody final List<CountryDto> countryDtoList) {
        LOGGER.info("Saving multiple days for a country");
        service.bulkSaveDays(countryDtoList);
        LOGGER.info("Days saved successfully for a country");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Days saved successfully");
    }

    @Operation(method = "GET",
            summary = "Поиск страны по имени",
            description = "Возвращает страну по её имени")
    @GetMapping("/findName")
    public ResponseEntity<CountryDto> findByNameCountry(
            @RequestParam final String name) {
        LOGGER.info("Finding country by name");
        CountryDto countryDto = service.findByNameCountry(name);
        if (countryDto != null) {
            LOGGER.info("Country found");
            return ResponseEntity.ok(countryDto);
        } else {
            LOGGER.info("Country not found");
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(method = "DELETE",
            summary = "Удалить страну по ID",
            description = "Удаляет страну из базы данных по её ID")
    @DeleteMapping("/deleteById")
    public String deleteCountryById(
            @RequestParam final Long id) {
        LOGGER.info("Deleting country by id: {}", id);
        try {
            service.deleteCountryById(id);
            LOGGER.info("Country deleted successfully: {}", id);
            return REDIRECT;
        } catch (EntityNotFoundException e) {
            LOGGER.error("Country not found: {}", id);
            return "redirect:/countries";
        }
    }
    @GetMapping("/deleteById")
    public String deleteCountry(@RequestParam Long name, Model model) {
        try {
            final CountryDto countries = service.findByNameCountry(String.valueOf(name));
            model.addAttribute("countries", countries);
            return "deleteCountry";
        } catch (EntityNotFoundException e) {
            LOGGER.error("Error deleting country by id", e.getMessage());
            model.addAttribute(ERROR_MESSAGE, e.getMessage());
            return ERROR_REDIRECT;
        }
    }


    @Operation(method = "PATCH",
            summary = "Обновить страну по имени",
            description = "Обновляет имя страны в базе данных")
    @PatchMapping("/updateByName")
    public ResponseEntity<CountryDto> updateCountryByName(
            @RequestParam final String name,
            @RequestParam final String newName) {
        LOGGER.info("Updating country name");
        CountryDto updatedCountryDto = service.
                updateCountryByName(name, newName);
        LOGGER.info("Country updated successfully: {}",
                updatedCountryDto.getName());
        return ResponseEntity.ok(updatedCountryDto);
    }

    @Operation(method = "GET",
            summary = "Поиск дней по имени страны и погодным условиям",
            description = "Возвращает дни,"
                    + "соответствующие заданным погодным условиям для страны")
    @GetMapping("/findByNameAndWeather")
    public ResponseEntity<List<DayDto>> findByCountryNameAndWeatherConditions(
            @RequestParam final String countryName,
            @RequestParam final String weatherConditions) {
        LOGGER.info("Finding days by country name and weather conditions");
        List<DayDto> dayDto = service.
                findByCountryNameAndWeatherConditions(
                        countryName, weatherConditions);
        LOGGER.info("Found {} days", dayDto.size());
        return ResponseEntity.ok(dayDto);
    }
}
