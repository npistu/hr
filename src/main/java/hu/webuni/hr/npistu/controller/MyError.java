package hu.webuni.hr.npistu.controller;

import org.springframework.validation.FieldError;

import java.util.List;

public class MyError {
    private String message;
    private List<FieldError> fieldsWithError;

    public MyError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getFieldsWithError() {
        return fieldsWithError;
    }

    public void setFieldsWithError(List<FieldError> fieldsWithError) {
        this.fieldsWithError = fieldsWithError;
    }
}
