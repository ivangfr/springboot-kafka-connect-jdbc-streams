package com.mycompany.storeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductDeletionException extends RuntimeException {

    public ProductDeletionException(String message) {
        super(message);
    }
}
