package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.exception.NonUniqueIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NonUniqueIdException.class)
    public ResponseEntity<MyError> handleNonUniqueIata(NonUniqueIdException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MyError(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyError> handleMethodArgumentNotValid(MethodArgumentNotValidException e, WebRequest request) {
        MyError myError = new MyError(e.getMessage());
        myError.setFieldsWithError(e.getBindingResult().getFieldErrors());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myError);
    }
}
