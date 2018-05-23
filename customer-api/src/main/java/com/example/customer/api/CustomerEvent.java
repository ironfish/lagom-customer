package com.example.customer.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.annotation.concurrent.Immutable;
import java.util.UUID;

/**
 * The Customer Event interface, wrapping implementations.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = com.example.customer.api.CustomerEvent.CustomerCreated.class, name = "customer-created")
})
public interface CustomerEvent {

    @Value
    @Builder
    @Immutable
    @JsonDeserialize
    @AllArgsConstructor
    final class CustomerCreated implements CustomerEvent {
        String lastName;
        String firstName;
        String initial;
        String dateOfBirth;
        Integer creditLimit = 0;
        String customerId = UUID.randomUUID().toString();
    }
}
