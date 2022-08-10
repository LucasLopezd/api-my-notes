package com.api.diary.exception;

public class NotFoundException extends RuntimeException {

    private static final String DESCRIPTION = "Resource not Found (404) ";

    public NotFoundException(String detail){
        super(DESCRIPTION + ", " + detail);
    }

}
