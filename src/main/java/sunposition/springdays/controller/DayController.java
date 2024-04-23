package sunposition.springdays.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.mapper.DayMapper;
import sunposition.springdays.model.Country;
import sunposition.springdays.model.Day;
import sunposition.springdays.service.CountryService;
import sunposition.springdays.service.DayService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v2/sunrise_sunset")
@Tag(name = "Day Controller",
        description = "API для работы с восходом и закатом солнца")
@AllArgsConstructor
public class DayController {
    private final DayService service;
    private final CountryService countryService;

    static final Logger LOGGER = LogManager.getLogger(DayController.class);
    private static final String REDIRECT = "redirect:/api/v2/sunrise_sunset";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_REDIRECT = "days/error";
    private static final String ATTRIBUTE = "day";

    @Operation(method = "GET",
            summary = "Получить все события восхода и заката",
            description = "Возвращает список всех событий восхода и заката")
    @GetMapping
    public String findAllSunriseSunset(final Model model) {
        LOGGER.info("Finding all sunrise and sunset times");
        List<Day> days = service.findAllSunriseSunset();
        model.addAttribute("days", days);
        LOGGER.info("Found {} sunrise and sunset times", days.size());
        return "days";
    }

    @GetMapping("/saveSunriseSunset")
    public String createDay(final @ModelAttribute("day") DayDto dayDto) {
        return "createDay";
    }

    @Operation(method = "POST",
            summary = "Сохранить событие "
                    + "восхода и заката",
            description = "Сохраняет новое событие "
                    + "восхода и заката в базе данных")
    @PostMapping("/saveSunriseSunset")
    public String saveSunriseSunset(
            @Valid @ModelAttribute("day") final DayDto dayDto,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().
                    forEach(error -> redirectAttributes.
                            addFlashAttribute(ERROR_MESSAGE,
                                    error.getDefaultMessage()));
            return "createDay";
        }
        try {
            LOGGER.info("Saving sunrise and sunset time");
            Optional<Country> country = countryService.
                    findByName(dayDto.getNameCountry());
            if (!country.isPresent()) {
                throw new HttpErrorExceptions.
                        CustomNotFoundException("Country not found");
            }
            Day day = DayMapper.toEntity(dayDto);
            day.setCountry(country.get());
            Day savedDay = service.saveSunriseSunset(day);
            DayDto savedDayDto = DayMapper.toDto(savedDay);
            LOGGER.info(
                    "Sunrise and sunset time saved successfully: {}",
                    savedDayDto.getDate());
            redirectAttributes.
                    addFlashAttribute("successMessage",
                            "Day saved successfully");
            return REDIRECT;
        } catch (HttpErrorExceptions.CustomNotFoundException e) {
            LOGGER.error("Country Not Found: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                    "Error saving day: " + e.getMessage());
            return "redirect:/createDay";
        } catch (Exception e) {
            LOGGER.error("An error occurred: {}", e.getMessage(), e);
            redirectAttributes.
                    addFlashAttribute(ERROR_MESSAGE,
                            "Error saving day: " + e.getMessage());
            return "redirect:/createDay";
        }
    }




    @Operation(method = "GET",
            summary = "Поиск события по местоположению",
            description = "Возвращает событие "
                    + "восхода и заката по его местоположению")
    @GetMapping("/findByLocation")
    public ResponseEntity<DayDto> findByLocation(
            @RequestParam final String location) {
        try {
            Day day = service.findByLocation(location);
            if (day == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            DayDto dayDto = DayMapper.toDto(day);
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (HttpErrorExceptions.CustomNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(method = "GET",
            summary = "Поиск события по координатам",
            description = "Возвращает событие "
                    + "восхода и заката по его координатам")
    @GetMapping("/findByCoordinates")
    public ResponseEntity<DayDto> findByCoordinates(
            @RequestParam(required = false) final String coordinates) {
        LOGGER.info("Finding sunrise and sunset time by coordinates");
        if (coordinates == null || coordinates.isEmpty()) {
            LOGGER.error("Coordinates parameter is missing");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Day day = service.findByCoordinates(coordinates);
            if (day == null) {
                LOGGER.error("Sunrise and sunset time "
                        + "not found for coordinates");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            DayDto dayDto = DayMapper.toDto(day);
            LOGGER.info("Sunrise and sunset time found for coordinates");
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error finding sunrise "
                    + "and sunset time by coordinates", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete/{location}")
    public String showDeleteForm(
            final @PathVariable String location, final Model model) {
        try {
            final Day day = service.findByLocation(location);
            model.addAttribute(ATTRIBUTE, day);
            return "deleteDay";
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, e.getMessage());
            return ERROR_REDIRECT;
        }
    }

    @Operation(method = "DELETE",
            summary = "Удалить город по местоположению",
            description = "Удаляет город из базы данных по его местоположению")
    @PostMapping(value = "/{location}", params = "_method=DELETE")
    public String deleteDayByLocation(
            @PathVariable final String location, final Model model) {
        LOGGER.info("Deleting city by location");
        try {
            service.deleteDayByLocation(location);
            LOGGER.info("City deleted successfully by location");
            return REDIRECT;
        } catch (Exception e) {
            LOGGER.error("Error deleting city by location", e);
            model.addAttribute(ERROR_MESSAGE, e.getMessage());
            return ERROR_REDIRECT;
        }
    }

    @PostMapping(value = "/{id}", params = "_method=PATCH")
    public String updateSunriseSunsetById(final @PathVariable Long id,
                                final @Valid @ModelAttribute("day")
                                DayDto dayDto,
                                          final BindingResult bindingResult,
                                final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().
                    forEach(error -> redirectAttributes.
                            addFlashAttribute(ERROR_MESSAGE,
                                    error.getDefaultMessage()));
            return "redirect:/api/v2/sunrise_sunset/update/" + id;
        }
        try {
            LOGGER.info("Updating a country");
            service.updateDay(id, dayDto);
            LOGGER.info("Day updated successfully");
            redirectAttributes.
                    addFlashAttribute("successMessage",
                            "Country updated successfully");
            return REDIRECT;
        } catch (EntityNotFoundException e) {
            LOGGER.error("Day not found", e);
            redirectAttributes.
                    addFlashAttribute(
                            ERROR_MESSAGE, "Country not found: "
                                    + e.getMessage());
            return ERROR_REDIRECT;
        }
    }


    @Operation(method = "GET",
            summary = "Отобразить форму обновления страны",
            description = "Отображает форму для обновления данных страны")
    @GetMapping("/update/{id}")
    public String showUpdateForm(final @PathVariable Long id,
                                 final Model model) {
        DayDto dayDto = service.findDayById(id);
        if (dayDto == null) {
            model.addAttribute(
                    ERROR_MESSAGE, "Страна с ID " + id + " не найдена");
            return ERROR_REDIRECT;
        }
        model.addAttribute("day", dayDto);
        return "updateDay";
    }
}
