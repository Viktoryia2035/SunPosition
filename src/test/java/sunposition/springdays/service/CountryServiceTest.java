package sunposition.springdays.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import sunposition.springdays.cache.DataCache;
import sunposition.springdays.dto.CountryDto;
import sunposition.springdays.dto.DayDto;
import sunposition.springdays.exception.HttpErrorExceptions;
import sunposition.springdays.mapper.CountryMapper;
import sunposition.springdays.model.Country;
import sunposition.springdays.model.Day;
import sunposition.springdays.repository.InMemoryCountryDAO;
import sunposition.springdays.repository.InMemoryDayDAO;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CountryServiceTest {

    @InjectMocks
    private CountryService countryService;

    @Mock
    private InMemoryCountryDAO repositoryOfCountry;

    @Mock
    private InMemoryDayDAO repositoryOfDay;


    @Mock
    private DataCache countryCache;

    @Mock
    private DataCache dayCache;

    @BeforeEach
    void setup() {
        when(countryCache.get("all")).thenReturn(Arrays.asList(new CountryDto("Country1"), new CountryDto("Country2")));
        when(dayCache.get("CountryName_Sunny")).thenReturn(Arrays.asList(new DayDto(), new DayDto()));

        countryService.setCountryCache(countryCache);
        countryService.setDayCache(dayCache);
    }

    @Test
    void testFindAll() {
        Country country1 = new Country();
        Country country2 = new Country();
        List<Country> countries = Arrays.asList(country1, country2);
        when(repositoryOfCountry.findAll()).thenReturn(countries);

        List<CountryDto> countryDtos = new ArrayList<>();
        for (Country country : countries) {
            CountryDto countryDto = CountryMapper.toDto(country);
            countryDtos.add(countryDto);
        }

        when(countryCache.get("all")).thenReturn(null).thenReturn(countryDtos);

        CountryService countryService = new CountryService(repositoryOfCountry, repositoryOfDay, countryCache, dayCache);

        List<CountryDto> resultFirstCall = countryService.findAll();
        List<CountryDto> resultSecondCall = countryService.findAll();

        verify(repositoryOfCountry, times(1)).findAll();

        verify(countryCache, times(2)).get("all");
        verify(countryCache, times(1)).put(eq("all"), anyList());

        assertEquals(2, resultFirstCall.size());
        assertEquals(2, resultSecondCall.size());

        Set<CountryDto> countryDtoSet = new HashSet<>(countryDtos);
        Set<CountryDto> resultFirstCallSet = new HashSet<>(resultFirstCall);
        Set<CountryDto> resultSecondCallSet = new HashSet<>(resultSecondCall);

        assertEquals(countryDtoSet, resultFirstCallSet);
        assertEquals(countryDtoSet, resultSecondCallSet);
    }


    @Test
    void testSaveCountry() {
        CountryDto countryDto = new CountryDto("Country1");
        countryDto.setName("CountryName");

        Country mockCountry = new Country();
        mockCountry.setName("CountryName");

        when(repositoryOfCountry.saveAndFlush(mockCountry)).thenReturn(mockCountry);

        doNothing().when(countryCache).put(eq("CountryName"), any(CountryDto.class));

        doNothing().when(countryCache).clear();

        CountryDto savedCountry = countryService.saveCountry(countryDto);

        assertEquals(countryDto.getName(), savedCountry.getName());

        verify(countryCache).put(eq("CountryName"), any(CountryDto.class));

        verify(countryCache).clear();
    }

    @Test
    void testSaveCountry_EmptyName() {
        CountryDto countryDto = new CountryDto("Country1");
        countryDto.setName("");

        try {
            countryService.saveCountry(countryDto);
        } catch (HttpErrorExceptions.CustomBadRequestException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testBulkSaveDaysWithCache() {
        CountryDto countryDto = new CountryDto("Country1");
        countryDto.setName("CountryName");

        Country mockCountry = new Country();
        mockCountry.setName("CountryName");

        when(repositoryOfCountry.findByName("CountryName")).thenReturn(Optional.of(mockCountry));

        doNothing().when(countryCache).clear();
        doNothing().when(dayCache).clear();

        countryService.bulkSaveDays(countryDto);

        verify(countryCache).clear();
        verify(dayCache).clear();
    }

    @Test
    void testBulkSaveDaysWithExceptionHandling() {
        CountryDto countryDto = new CountryDto("Country1");
        countryDto.setName("CountryName");

        when(repositoryOfCountry.findByName("CountryName")).thenReturn(Optional.empty());

        Exception exception = assertThrows(HttpErrorExceptions.CustomInternalServerErrorException.class, () -> {
            countryService.bulkSaveDays(countryDto);
        });

        assertEquals("An error occurred while saving days", exception.getMessage());

        verify(countryCache, never()).clear();
        verify(dayCache, never()).clear();
    }

    @Test
    void testFindByNameCountry() {
        CountryDto countryDto = new CountryDto("Country1");
        countryDto.setName("CountryName");

        when(countryCache.get("CountryName")).thenReturn(null);

        Country mockCountry = new Country();
        mockCountry.setName("CountryName");
        when(repositoryOfCountry.findByName("CountryName")).thenReturn(Optional.of(mockCountry));

        CountryDto foundCountry = countryService.findByNameCountry("CountryName");

        assertEquals(countryDto.getName(), foundCountry.getName());

        verify(countryCache).get("CountryName");

        verify(countryCache).put(any(), any(CountryDto.class));
    }

    @Test
    void testFindByNameCountry_NotFound() {
        when(countryCache.get("CountryName")).thenReturn(null);
        when(repositoryOfCountry.findByName("CountryName")).thenReturn(Optional.empty());

        Exception exception = assertThrows(HttpErrorExceptions.CustomNotFoundException.class, () -> {
            countryService.findByNameCountry("CountryName");
        });

        assertEquals("Country not found", exception.getMessage());
    }

    @Test
    void testDeleteCountryById() {
        InMemoryCountryDAO repositoryOfCountry = mock(InMemoryCountryDAO.class);
        InMemoryDayDAO repositoryOfDay = mock(InMemoryDayDAO.class);
        DataCache countryCache = mock(DataCache.class);
        CountryService countryService = new CountryService(repositoryOfCountry, repositoryOfDay, countryCache, dayCache);

        Country mockCountry = new Country();
        mockCountry.setName("CountryName");
        List<Day> daysToDelete = new ArrayList<>();
        mockCountry.setDays(daysToDelete);

        when(repositoryOfCountry.findById(1L)).thenReturn(Optional.of(mockCountry));
        doNothing().when(repositoryOfDay).deleteAll(anyList());

        countryService.deleteCountryById(1L);

        verify(countryCache, times(1)).clear();
    }

    @Test
    void testDeleteCountryById_NotFound() {
        when(repositoryOfCountry.findById(1L)).thenReturn(Optional.empty());

        try {
            countryService.deleteCountryById(1L);
        } catch (HttpErrorExceptions.CustomNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testUpdateCountryByName() {
        InMemoryCountryDAO repositoryOfCountry = mock(InMemoryCountryDAO.class);
        DataCache countryCache = mock(DataCache.class);
        CountryService countryService = new CountryService(repositoryOfCountry, repositoryOfDay, countryCache, dayCache);

        CountryDto countryDto = new CountryDto("Country1");
        countryDto.setName("OldName");
        Country existingCountry = new Country();
        existingCountry.setName("OldName");

        when(repositoryOfCountry.findByName("OldName")).thenReturn(Optional.of(existingCountry));
        when(repositoryOfCountry.findByName("NewName")).thenReturn(Optional.empty());

        CountryDto updatedCountry = countryService.updateCountryByName("OldName", "NewName");

        verify(countryCache, times(2)).remove("OldName");
        verify(countryCache, times(1)).put(any(), any(CountryDto.class));

        assertEquals("NewName", updatedCountry.getName());
    }

    @Test
    void testFindByCountryNameAndWeatherConditions() {
        InMemoryDayDAO repositoryOfDay = mock(InMemoryDayDAO.class);
        DataCache dayCache = mock(DataCache.class);
        CountryService countryService = new CountryService(repositoryOfCountry, repositoryOfDay, countryCache, dayCache);

        Day mockDay1 = new Day();
        Day mockDay2 = new Day();
        List<Day> mockDays = Arrays.asList(mockDay1, mockDay2);

        when(repositoryOfDay.findByCountryNameAndWeatherConditions("CountryName", "Sunny")).thenReturn(mockDays);

        List<DayDto> result = countryService.findByCountryNameAndWeatherConditions("CountryName", "Sunny");

        String cacheKey = "CountryName_Sunny";
        verify(dayCache, times(1)).get(cacheKey);
        verify(dayCache, times(1)).put(eq(cacheKey), anyList());

        assertEquals(2, result.size());
    }

    @Test
    void testFindByCountryNameAndWeatherConditions_NotFound() {
        when(dayCache.get("CountryName_Sunny")).thenReturn(null);
        when(repositoryOfDay.findByCountryNameAndWeatherConditions("CountryName", "Sunny")).thenReturn(null);

        try {
            countryService.findByCountryNameAndWeatherConditions("CountryName", "Sunny");
        } catch (HttpErrorExceptions.CustomNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
