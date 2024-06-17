package com.manuelnovela.TravelAssistant.infrastructure.validator.interfaces;

import com.manuelnovela.TravelAssistant.infrastructure.validator.CurrencyCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = CurrencyCodeValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface CurrencyCode {
    String message() default "Please provide a valid currency code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
