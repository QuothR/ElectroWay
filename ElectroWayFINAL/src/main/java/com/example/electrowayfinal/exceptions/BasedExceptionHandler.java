package com.example.electrowayfinal.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class BasedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {

        List<String> details = new ArrayList<>();
        ErrorResponse error  = new ErrorResponse("Invalid Data", details);
        details.add(ex.getLocalizedMessage());

        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex) {

        List<String> details = new ArrayList<>();
        ErrorResponse error  = new ErrorResponse("Record Not Found", details);
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> sqlError(DataIntegrityViolationException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error  = new ErrorResponse("SQL Error",details);
        details.add(ex.getRootCause().getMessage());

        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        ErrorResponse error  = new ErrorResponse("Invalid Data", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}
