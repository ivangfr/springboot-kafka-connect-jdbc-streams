package com.ivanfranchin.storeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerDeletionException extends RuntimeException {

    public CustomerDeletionException(Long id) {
        super(String.format("Customer with id '%s' cannot be deleted", id));
    }
}
