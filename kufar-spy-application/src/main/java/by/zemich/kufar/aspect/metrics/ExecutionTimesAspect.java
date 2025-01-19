package by.zemich.kufar.aspect.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ExecutionTimesAspect {
    private final MeterRegistry meterRegistry;

    public ExecutionTimesAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Around("by.zemich.kufar.aspect.pointcut.SchedulerPointcut.scheduledMethods()")
    public void scheduledMethodTimeTaken(ProceedingJoinPoint proceedingJoinPoint) {
        Tags methodTag = Tags.of("method", getMethodName(proceedingJoinPoint));
        methodTag.and(Tag.of("class", proceedingJoinPoint.getTarget().getClass().getName()));
        tryToPerformAndGetTakenTime(proceedingJoinPoint, "scheduled", methodTag);

    }

    @Around("within(by.zemich.kufar.application.service.api.PostManager+)")
    public Object postCreateMethodsTimeTaken(ProceedingJoinPoint proceedingJoinPoint) {
        Tags methodTag = Tags.of("method", getMethodName(proceedingJoinPoint));
        methodTag.and(Tag.of("class", proceedingJoinPoint.getTarget().getClass().getName()));
        return tryToPerformAndGetTakenTime(proceedingJoinPoint, "post_creation", methodTag);
    }

    private Object tryToPerformAndGetTakenTime(ProceedingJoinPoint joinPoint, String topic, Tags tags) {
        Timer timer = Timer.builder(topic + ".execution.duration")
                .tags(tags)
                .description("Time taken for execute method")
                .register(meterRegistry);

        try {
            return timer.recordCallable(() -> {
                try {
                    return joinPoint.proceed();
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during timer recording", e);
        }

    }

    private String getMethodName(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.getSignature().getName();
    }

    private Tags getTags(Map<String, String> tagsMap) {
        List<Tag> tags = tagsMap.entrySet().stream()
                .map(entry -> Tag.of(entry.getKey(), entry.getValue()))
                .toList();
        return Tags.of(tags);
    }
}
