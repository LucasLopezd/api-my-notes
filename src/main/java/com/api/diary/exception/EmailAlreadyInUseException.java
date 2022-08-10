package com.api.diary.exception;

public class EmailAlreadyInUseException extends RuntimeException {

    private static final String DESCRIPTION = "Email already in use by other User";

    public EmailAlreadyInUseException(String detail){
        super(DESCRIPTION + ", " + detail);
    }

}
