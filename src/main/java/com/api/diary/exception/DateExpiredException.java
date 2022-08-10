package com.api.diary.exception;

public class DateExpiredException extends RuntimeException{
    
    private static final String DESCRIPTION = "You can't set a date previous to current";

    public DateExpiredException(String detail){
        super(DESCRIPTION + ", " + detail);
    }
}
