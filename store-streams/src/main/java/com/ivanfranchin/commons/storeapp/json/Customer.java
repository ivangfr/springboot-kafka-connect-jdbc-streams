package com.ivanfranchin.commons.storeapp.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record Customer(Long id, String name, String email, String address, String phone,
                       @JsonProperty("created_at") Date createdAt) {
}
