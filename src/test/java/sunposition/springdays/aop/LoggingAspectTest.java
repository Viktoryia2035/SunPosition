package sunposition.springdays.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import sunposition.springdays.model.Country;

import static org.mockito.Mockito.*;

class LoggingAspectTest {

    private LoggingAspect loggingAspect;
    private JoinPoint joinPoint;
    private Logger mockLogger;
    private Signature mockSignature;

    @BeforeEach
    void setUp() {
        loggingAspect = new LoggingAspect();
        joinPoint = mock(JoinPoint.class);
        mockLogger = mock(Logger.class);
        mockSignature = mock(Signature.class);
        LoggingAspect.log = mockLogger;

        when(joinPoint.getSignature()).thenReturn(mockSignature);
    }

    @Test
    void testBeforeAdvice() {
        when(mockSignature.getName()).thenReturn("testMethod");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"arg1", "arg2"});

        loggingAspect.beforeAdvice(joinPoint);

        verify(mockLogger, times(1)).info("Entering method: {}", "testMethod");
        verify(joinPoint, times(1)).getArgs();
    }

    @Test
    void testAfterReturningAdvice() {
        Object result = "testResult";
        when(mockSignature.getName()).thenReturn("testMethod");

        loggingAspect.afterReturningAdvice(joinPoint, result);

        verify(mockLogger, times(1)).info("Exiting method: {}, with result: {}", "testMethod", result.toString());
    }

    @Test
    void testAfterAdvice() {
        when(mockSignature.getName()).thenReturn("testMethod");

        loggingAspect.afterAdvice(joinPoint);

        verify(mockLogger, times(1)).info("Method: {} has completed", "testMethod");
    }

    @Test
    void testAfterThrowingAdvice() {
        Throwable ex = new RuntimeException("Test exception");
        when(mockSignature.getName()).thenReturn("testMethod");

        loggingAspect.afterThrowingAdvice(joinPoint, ex);

        verify(mockLogger, times(1)).error("Exception thrown in method: {} - Error: {}", "testMethod", ex.getClass().getSimpleName(), ex);
    }

    @Test
    void testLogArguments() {
        // Подготовка
        when(joinPoint.getArgs()).thenReturn(new Object[]{"arg1", "arg2"});

        // Вызов метода
        loggingAspect.logArguments(joinPoint);

        // Проверка
        verify(mockLogger, times(1)).info("Method arguments: {}", "arg1, arg2");
    }

    @Test
    void testBeforeAdviceWithLoggerAnnotation() {
        // Подготовка
        when(joinPoint.getSignature()).thenReturn(mockSignature);
        when(mockSignature.getName()).thenReturn("updateCountryName");
        when(joinPoint.getArgs()).thenReturn(new Object[]{new Country(), "New Name"});

        // Вызов метода
        loggingAspect.beforeAdvice(joinPoint);

        // Проверка
        verify(mockLogger, times(1)).info("Entering method: {}", "updateCountryName");
        verify(mockLogger, times(1)).info("Method arguments: {}", "Country@hashcode, New Name");
    }

    @Test
    void testAfterReturningAdviceWithLoggerAnnotation() {
        // Подготовка
        when(joinPoint.getSignature()).thenReturn(mockSignature);
        when(mockSignature.getName()).thenReturn("updateCountryName");
        Country updatedCountry = new Country();
        updatedCountry.setName("New Name");

        // Вызов метода
        loggingAspect.afterReturningAdvice(joinPoint, updatedCountry);

        // Проверка
        verify(mockLogger, times(1)).info("Exiting method: {}, with result: {}", "updateCountryName", updatedCountry.toString());
    }


}
