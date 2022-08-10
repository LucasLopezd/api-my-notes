package com.api.diary.exception;

public class UnauthorizedException extends RuntimeException {

    private static final String DESCRIPTION = "You do not have the credentials, you must log in first";

    public UnauthorizedException(String detail){
        super(DESCRIPTION + ", " + detail);
    }
    
}
