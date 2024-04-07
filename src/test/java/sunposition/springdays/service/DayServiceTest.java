package sunposition.springdays.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.model.Day;
import sunposition.springdays.repository.InMemoryDayDAO;
import sunposition.springdays.cache.DataCache;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DayServiceTest {

    private static final String LOCATION_PREFIX = "location_";

    private static final String COORDINATES_PREFIX = "coordinates_";

    @Test
    void testFindAllSunriseSunset() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        Day mockDay1 = new Day();
        Day mockDay2 = new Day();
        List<Day> days = Arrays.asList(mockDay1, mockDay2);

        when(repository.findAll()).thenReturn(days);

        List<Day> result = dayService.findAllSunriseSunset();

        assertEquals(2, result.size());

        for (Day day : days) {
            String cacheKey = LOCATION_PREFIX + day.getLocation();
            verify(dayCache, times(2)).put(eq(cacheKey), any(DayDto.class));
        }
    }

    @Test
    void testSaveSunriseSunset() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        Day day = new Day();

        when(repository.save(day)).thenReturn(day);

        Day savedDay = dayService.saveSunriseSunset(day, "POST");

        assertEquals(day, savedDay);

        String cacheKey = LOCATION_PREFIX + day.getLocation();
        verify(dayCache, times(1)).put(any(), any(DayDto.class));
        verify(dayCache, times(1)).clear();
    }

    @Test
    void testSaveSunriseSunset_InvalidHttpMethod() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        Day day = new Day();

        try {
            dayService.saveSunriseSunset(day, "GET");
            fail("Expected CustomMethodNotAllowedException to be thrown");
        } catch (HttpErrorExceptions.CustomMethodNotAllowedException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        verify(dayCache, times(0)).put(anyString(), any(DayDto.class));
        verify(dayCache, times(0)).clear();
    }

    @Test
    void testFindByLocation() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        Day day = new Day();
        day.setLocation("Location");

        when(repository.findByLocation("Location")).thenReturn(day);

        Day foundDay = dayService.findByLocation("Location");

        assertEquals(day, foundDay);

        String cacheKey = LOCATION_PREFIX + "Location";
        verify(dayCache, times(1)).get(cacheKey);
        verify(dayCache, times(1)).put(any(), any(DayDto.class));
    }

    @Test
    void testFindByLocation_NullLocation() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        try {
            dayService.findByLocation(null);
            fail("Expected CustomBadRequestException to be thrown");
        } catch (HttpErrorExceptions.CustomBadRequestException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        verify(dayCache, times(0)).get(anyString());
        verify(dayCache, times(0)).put(anyString(), any(DayDto.class));
    }

    @Test
    void testFindByLocation_EmptyLocation() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        try {
            dayService.findByLocation("");
            fail("Expected CustomBadRequestException to be thrown");
        } catch (HttpErrorExceptions.CustomBadRequestException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        verify(dayCache, times(0)).get(anyString());
        verify(dayCache, times(0)).put(anyString(), any(DayDto.class));
    }

    @Test
    void testFindByCoordinates() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);
        Day day = new Day();
        when(repository.findByCoordinates("Coordinates")).thenReturn(day);
        Day foundDay = dayService.findByCoordinates("Coordinates");
        assertEquals(day, foundDay);
        String cacheKey = COORDINATES_PREFIX + "Coordinates";
        verify(dayCache, times(1)).get(cacheKey);
        verify(dayCache, times(1)).put(any(), any(DayDto.class));
    }

    @Test
    void testFindByCoordinates_NullCoordinates() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        try {
            dayService.findByCoordinates(null);
            fail("Expected CustomBadRequestException to be thrown");
        } catch (HttpErrorExceptions.CustomBadRequestException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        verify(dayCache, times(0)).get(anyString());
        verify(dayCache, times(0)).put(anyString(), any(DayDto.class));
    }

    @Test
    void testFindByCoordinates_EmptyCoordinates() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        try {
            dayService.findByCoordinates("");
            fail("Expected CustomBadRequestException to be thrown");
        } catch (HttpErrorExceptions.CustomBadRequestException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        verify(dayCache, times(0)).get(anyString());
        verify(dayCache, times(0)).put(anyString(), any(DayDto.class));
    }

    @Test
    void testDeleteDayByCoordinates() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        Day day = new Day();
        day.setCoordinates("Coordinates");

        when(repository.findByCoordinates("Coordinates")).thenReturn(day);

        dayService.deleteDayByCoordinates("Coordinates");

        String cacheKey = COORDINATES_PREFIX + "Coordinates";

        verify(dayCache, times(1)).remove(cacheKey);
        verify(dayCache, times(1)).clear();
    }

    @Test
    void testDeleteDayByCoordinates_NotFound() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        when(repository.findByCoordinates("NonExistentCoordinates")).thenReturn(null);

        try {
            dayService.deleteDayByCoordinates("NonExistentCoordinates");
            fail("Expected CustomNotFoundException to be thrown");
        } catch (HttpErrorExceptions.CustomNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        verify(dayCache, times(0)).remove(anyString());
        verify(dayCache, times(0)).clear();
    }

    @Test
    void testUpdateSunriseSunset() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        Day day = new Day();
        day.setLocation("Location");

        when(repository.findByLocation("Location")).thenReturn(day);
        when(repository.save(day)).thenReturn(day);

        Day updatedDay = dayService.updateSunriseSunset("Location", "Coordinates", LocalDate.now());

        assertEquals(day, updatedDay);

        String locationCacheKey = LOCATION_PREFIX + "Location";
        verify(dayCache, times(1)).remove(locationCacheKey);
        verify(dayCache, times(2)).put(any(), any(DayDto.class));
        verify(dayCache, times(1)).clear();
    }

    @Test
    void testUpdateSunriseSunset_NotFound() {
        InMemoryDayDAO repository = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        DayService dayService = new DayService(repository, dayCache);

        when(repository.findByLocation("NonExistentLocation")).thenReturn(null);

        try {
            dayService.updateSunriseSunset("NonExistentLocation", "Coordinates", LocalDate.now());
        } catch (HttpErrorExceptions.CustomNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        verify(dayCache, times(0)).remove(anyString());
        verify(dayCache, times(0)).put(anyString(), any(DayDto.class));
        verify(dayCache, times(0)).clear();
    }
}