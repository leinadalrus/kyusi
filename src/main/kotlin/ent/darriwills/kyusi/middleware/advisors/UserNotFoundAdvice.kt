package ent.darriwills.kyusi.middleware.advisors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

import ent.darriwills.kyusi.middleware.exceptions.UserNotFoundException

public class UserNotFoundAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun userNotFoundHandler(UserNotFoundException exception): String {
        return exception.getMessage()
    }
}