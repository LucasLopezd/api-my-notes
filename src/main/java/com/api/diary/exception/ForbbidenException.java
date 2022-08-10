package com.api.diary.exception;

public class ForbbidenException extends RuntimeException {

    private static final String DESCRIPTION = "You are not authorized";

    public ForbbidenException(String detail){
        super(DESCRIPTION + ", " + detail);
    }
}
