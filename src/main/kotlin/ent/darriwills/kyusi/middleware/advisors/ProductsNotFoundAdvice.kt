package ent.darriwills.kyusi.middleware.advisors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

import ent.darriwills.kyusi.middleware.exceptions.ProductsNotFoundException

public class ProductsNotFoundAdvice {
    @ExceptionHandler(ProductsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun productsNotFoundHandler(ProductsNotFoundException exception): String {
        return exception.getMessage()
    }
}