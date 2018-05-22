package com.example.customer.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.Value;

@SuppressWarnings("serial")
@Value
@JsonDeserialize
public final class CustomerState implements CompressedJsonable {

    public final String message;
    public final String timestamp;

    @JsonCreator
    public CustomerState(String message, String timestamp) {
        this.message = Preconditions.checkNotNull(message, "message");
        this.timestamp = Preconditions.checkNotNull(timestamp, "timestamp");
    }
}
