package com.example.electrowayfinal.Validation;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ResponseHeader;

import java.lang.annotation.Annotation;
import java.util.Map;

public class ApiResponses implements ApiResponse {
    private Map<String,String> error;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }

    @Override
    public int code() {
        return 69;
    }

    @Override
    public String message() {
        return getMessage();
    }

    @Override
    public Class<?> response() {
        return null;
    }

    @Override
    public String reference() {
        return "Cock";
    }

    @Override
    public ResponseHeader[] responseHeaders() {
        return new ResponseHeader[0];
    }

    @Override
    public String responseContainer() {
        return null;
    }

    @Override
    public Example examples() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

}
