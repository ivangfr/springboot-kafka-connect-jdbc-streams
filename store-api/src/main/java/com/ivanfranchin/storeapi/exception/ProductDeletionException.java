package com.ivanfranchin.storeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductDeletionException extends RuntimeException {

    public ProductDeletionException(Long id) {
        super(String.format("Product with id '%s' cannot be deleted", id));
    }
}
