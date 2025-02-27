package by.zemich.telegrambotservice.mocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KufarAdvertisementInjection {
    String category() default "Телефоны и планшеты";
    boolean emptyParam() default true;
}
