package com.manuelnovela.TravelAssistant.infrastructure.validator.interfaces;

import com.manuelnovela.TravelAssistant.infrastructure.validator.CountryCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = CountryCodeValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface ValidCountryCode {
    String message() default "Código de país inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
