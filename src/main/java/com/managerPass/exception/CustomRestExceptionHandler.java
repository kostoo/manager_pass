package com.managerPass.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    public static ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {

        ArrayList<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(
                    String.format("%s  %s : %s)", violation.getRootBeanClass().getName(), violation.getPropertyPath(),
                            violation.getMessage())
            );
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
