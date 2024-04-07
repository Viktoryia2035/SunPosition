package sunposition.springdays.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    protected static Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* sunposition.springdays.service.DayService.*(..))")
    public void dayServiceMethods() {
    }

    @Pointcut("execution(* sunposition.springdays.service.CountryService.*(..))")
    public void countryServiceMethods() {
    }

    @Before("dayServiceMethods() || countryServiceMethods()")
    public void beforeAdvice(final JoinPoint joinPoint) {
        log.info("Entering method: {}", joinPoint.getSignature().getName());
        logArguments(joinPoint);
    }

    @AfterReturning(value = "dayServiceMethods() || countryServiceMethods()", returning = "result")
    public void afterReturningAdvice(final JoinPoint joinPoint, final Object result) {
        log.info("Exiting method: {}, with result: {}", joinPoint.getSignature().getName(), result);
    }

    @After(value = "dayServiceMethods() || countryServiceMethods()")
    public void afterAdvice(final JoinPoint joinPoint) {
        log.info("Method: {} has completed", joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "dayServiceMethods() || countryServiceMethods()", throwing = "ex")
    public void afterThrowingAdvice(final JoinPoint joinPoint, final Throwable ex) {
        log.error("Exception thrown in method: {} - Error: {}", joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(), ex);
    }

    void logArguments(final JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            StringBuilder argsString = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    argsString.append(args[i]);
                    if (i < args.length - 1) {
                        argsString.append(", ");
                    }
                }
            }
            log.info("Method arguments: {}", argsString);
        }
    }
}