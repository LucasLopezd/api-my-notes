package com.api.diary.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDetails {

    private final String exception;
    private final String statusCode;
    private final String message;
    private final String path;

    public ExceptionDetails(Exception exception, String  statusCode, String path){
        this.exception = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.statusCode = statusCode;
        this.path = path;
    }

    
}

    
