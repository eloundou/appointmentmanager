package com.example.appointmentmanager.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidatorContrainst.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneValidator {

    String message() default "Le format du numéro de téléphone est incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
