package com.mycompany.storeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerDeletionException extends RuntimeException {

    public CustomerDeletionException(String message) {
        super(message);
    }
}
