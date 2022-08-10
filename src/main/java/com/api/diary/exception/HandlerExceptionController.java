package com.api.diary.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class HandlerExceptionController {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public ExceptionDetails notFoundRequest(HttpServletRequest request, Exception exception){
        return new ExceptionDetails(exception, "404", request.getRequestURI());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            NoSuchElementException.class,
            DateExpiredException.class,
            org.springframework.dao.DuplicateKeyException.class,
            org.springframework.web.HttpMediaTypeNotSupportedException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class,
            org.springframework.web.bind.MissingRequestHeaderException.class,
            org.springframework.web.bind.MissingServletRequestParameterException.class,
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class
    })
    @ResponseBody
    public ExceptionDetails badRequest(HttpServletRequest request, Exception exception){
        return new ExceptionDetails(exception, "400", request.getRequestURI());
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler({ForbbidenException.class})
    @ResponseBody
    public ExceptionDetails forbbidenRequest(HttpServletRequest request, Exception exception){
        return new ExceptionDetails(exception, "403", request.getRequestURI());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler({EmailAlreadyInUseException.class})
    @ResponseBody
    public ExceptionDetails emailAlreadyInUse(HttpServletRequest request, Exception exception){
        return new ExceptionDetails(exception, "409", request.getRequestURI());
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler({
            UnauthorizedException.class,
            org.springframework.security.access.AccessDeniedException.class
    })
    @ResponseBody
    public void unauthorized(){}

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, IOException.class})
    @ResponseBody
    public ExceptionDetails fatalErrorUnexpectedEsception(HttpServletRequest request, Exception exception){
        return new ExceptionDetails(exception, "500", request.getRequestURI());
    }

}