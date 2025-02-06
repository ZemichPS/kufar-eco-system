package by.zemich.parser.aspect.pointcut;

import by.zemich.parser.domain.model.Advertisement;
import org.aspectj.lang.annotation.Pointcut;

public class PublisherPointcut {

    @Pointcut("execution(boolean by.zemich.parser.application.service.api.AdvertisementPublisher.publish(..)) && args(advertisement)")
    public void publishMethod(Advertisement advertisement) {}

    @Pointcut("execution(boolean by.zemich.parser.application.service.api.AdvertisementPublisher.publish(..))")
    public void publishMethodWithoutArgs() {}
}
