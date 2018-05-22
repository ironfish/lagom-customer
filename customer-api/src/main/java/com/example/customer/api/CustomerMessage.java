package com.example.customer.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import lombok.Value;

@Value
@JsonDeserialize
public class CustomerMessage {

    public final String message;

    @JsonCreator
    public CustomerMessage(String message) {
        this.message = Preconditions.checkNotNull(message, "message");
    }
}
