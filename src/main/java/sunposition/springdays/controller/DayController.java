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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.mapper.DayMapper;
import sunposition.springdays.model.Day;
import sunposition.springdays.service.CounterService;
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
    public String createDay(@ModelAttribute("day") DayDto dayDto) {
        return "createDay";
    }

    @Operation(method = "POST",
            summary = "Сохранить событие "
                    + "восхода и заката",
            description = "Сохраняет новое событие "
                    + "восхода и заката в базе данных")
    @PostMapping("/saveSunriseSunset")
    public String saveSunriseSunset(
            @Valid @ModelAttribute("day") final DayDto dayDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> redirectAttributes.addFlashAttribute(ERROR_MESSAGE, error.getDefaultMessage()));
            return "createDay";
        }
        try {
            LOGGER.info("Saving sunrise and sunset time");
            Day day = DayMapper.toEntity(dayDto);
            Day savedDay = service.saveSunriseSunset(day, "POST");
            DayDto savedDayDto = DayMapper.toDto(savedDay);
            LOGGER.info(
                    "Sunrise and sunset time saved successfully: {}",
                    savedDayDto.getDate());
            redirectAttributes.addFlashAttribute("successMessage", "Day saved successfully");
            return REDIRECT;
        } catch (HttpErrorExceptions.CustomMethodNotAllowedException e) {
            LOGGER.error("Method Not Allowed: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Error saving day: " + e.getMessage());
            return "redirect:/createDay";
        } catch (Exception e) {
            LOGGER.error("An error occurred: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Error saving day: " + e.getMessage());
            return "redirect:/createDay";
        }
    }

    @Operation(method = "GET",
            summary = "Поиск события по местоположению",
            description = "Возвращает событие "
                    + "восхода и заката по его местоположению")
    @GetMapping("/findByLocation")
    public ResponseEntity<DayDto> findByLocation(@RequestParam final String location) {
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
    public String showDeleteForm(@PathVariable String location, Model model) {
        try {
            final Day day = service.findByLocation(location);
            model.addAttribute(ATTRIBUTE, day);
            return "deleteDay";
        } catch (Exception e) {
            LOGGER.error("Error deleting day by location", e.getMessage());
            model.addAttribute(ERROR_MESSAGE, e.getMessage());
            return ERROR_REDIRECT;
        }
    }

    @Operation(method = "DELETE",
            summary = "Удалить город по местоположению",
            description = "Удаляет город из базы данных по его местоположению")
    @PostMapping(value = "/{location}", params = "_method=DELETE")
    public String deleteDayByLocation(@PathVariable final String location, Model model) {
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

    @Operation(method = "PUT",
            summary = "Обновить событие восхода и заката",
            description = "Обновляет местоположение события в базе данных")
    @PutMapping("/{id}")
    public String updateSunriseSunsetById(
            @Valid @ModelAttribute("day") DayDto dayDto,
            BindingResult bindingResult,
            @PathVariable Long id,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERROR_MESSAGE, "Ошибка валидации данных");
            return "updateDay";
        }
        try {
            CounterService.incrementRequestCount();
            int requestCount = CounterService.getRequestCount();
            LOGGER.info("Текущее количество запросов: {}", requestCount);

            LOGGER.info("Обновление события восхода и заката по id");
            service.updateDayById(id, dayDto);
            LOGGER.info("Событие обновлено успешно");
            return REDIRECT;
        } catch (EntityNotFoundException e) {
            model.addAttribute(ERROR_MESSAGE, e.getMessage());
            return ERROR_REDIRECT;
        } catch (Exception e) {
            LOGGER.error("Ошибка обновления события: {}", e.getMessage(), e);
            model.addAttribute(ERROR_MESSAGE, "Ошибка обновления события: " + e.getMessage());
            return ERROR_REDIRECT;
        }
    }


    @GetMapping("/update/{id}")
    public String updateDayById(@PathVariable Long id, Model model) {
        try {
            final Optional<Day> dayOptional = service.findById(id);
            model.addAttribute(ATTRIBUTE, dayOptional);
            if (dayOptional.isPresent()) {
                Day day = dayOptional.get();
                model.addAttribute("day", day);
                return "updateDay";
            } else {
                model.addAttribute(ERROR_MESSAGE, "Событие с ID " + id + " не найдено");
                return ERROR_REDIRECT;
            }
        } catch (EntityNotFoundException e) {
            LOGGER.error("Error deleting city by location", e.getMessage());
            model.addAttribute(ERROR_MESSAGE, e.getMessage());
            return ERROR_REDIRECT;
        }
    }
}
