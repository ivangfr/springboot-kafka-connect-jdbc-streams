package com.ivanfranchin.storeapi.rest.dto;

public record CustomerResponse(Long id, String name, String email, String address, String phone) {
}
