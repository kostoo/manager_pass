package com.managerPass.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(final HttpStatus status, final String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
