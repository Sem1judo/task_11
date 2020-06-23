package com.ua.foxminded.task_12.validation;

import com.ua.foxminded.task_12.exceptions.ServiceException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class ValidatorEntity<T> {

    private javax.validation.Validator getValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    public void validate(@Valid T t) {
        Set<ConstraintViolation<T>> constraintViolations = getValidatorInstance().validate(t);
        if (!constraintViolations.isEmpty()) {
            throw new ServiceException("Data is not valid: " + constraintViolations.iterator().next());
        }
    }
}
