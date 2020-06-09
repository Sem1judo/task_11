package com.ua.foxminded.task_11.validation;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

@Component
public class ValidatorEntity {

    public javax.validation.Validator getValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        return validator;
    }

}
