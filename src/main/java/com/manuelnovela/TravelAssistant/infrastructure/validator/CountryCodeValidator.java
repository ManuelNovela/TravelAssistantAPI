package com.manuelnovela.TravelAssistant.infrastructure.validator;

import com.manuelnovela.TravelAssistant.infrastructure.validator.interfaces.ValidCountryCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {

    //TO-DO: More contries
    private static final List<String> validCountryCodes = Arrays.asList("MZ", "PT", "BR");

    @Override
    public void initialize(ValidCountryCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String countryCode, ConstraintValidatorContext context) {
        return countryCode != null && validCountryCodes.contains(countryCode.toUpperCase());
    }
}
