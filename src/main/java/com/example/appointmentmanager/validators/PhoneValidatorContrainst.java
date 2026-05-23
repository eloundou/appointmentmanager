package com.example.appointmentmanager.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidatorContrainst implements ConstraintValidator<PhoneValidator, Integer> {

    // 9-digit number starting with valid Cameroonian prefixes
    private static final Pattern PATTERN = Pattern.compile(
            "^(?:" +
                    "6(?:5[0-9]|7[0-9]|8[0-9]|9[0-9])" +  // mobile: 65x, 67x, 68x, 69x
                    "|" +
                    "2(?:22|33|42|43)" +                    // Camtel landlines
                    ")" +
                    "\\d{6}$"
    );

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return PATTERN.matcher(value.toString()).matches();
    }
}
