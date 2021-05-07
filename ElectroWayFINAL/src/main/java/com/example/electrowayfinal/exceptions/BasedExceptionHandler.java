package com.example.electrowayfinal.exceptions;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class BasedExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO this should be removed
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {

        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Invalid Data", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WrongAccessException.class)
    public final ResponseEntity<Object> handleWrongAccessException(WrongAccessException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Wrong access", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WrongUserInServiceException.class)
    public final ResponseEntity<Object> handleWrongUserInServiceException(WrongUserInServiceException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Wrong user in service", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MessagingException.class)
    public final ResponseEntity<Object> handleMessagingException(MessagingException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Messaging exception", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Bad credentials", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SignatureException.class)
    public final ResponseEntity<Object> handleSignatureException(SignatureException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Signature exception", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public final ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Malformed jwt", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public final ResponseEntity<Object> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Unsupported jwt", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Illegal arguments", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DisabledException.class)
    public final ResponseEntity<Object> handleDisabledException(DisabledException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("User disabled", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("Record Not Found", details);
        details.add(ex.getLocalizedMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> sqlError(DataIntegrityViolationException ex) {
        List<String> details = new ArrayList<>();
        ErrorResponse error = new ErrorResponse("SQL Error", details);
        details.add(ex.getRootCause().getMessage());
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        ErrorResponse error = new ErrorResponse("Invalid Data", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
