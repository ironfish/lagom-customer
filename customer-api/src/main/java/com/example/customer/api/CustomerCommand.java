package com.example.customer.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

public interface CustomerCommand extends Jsonable {

    @Value
    @Builder
    @JsonDeserialize
    @AllArgsConstructor
    class CreateCustomer implements CustomerCommand, PersistentEntity.ReplyType<String> {

        String customerId;
        String lastName;
        String firstName;
        String initial;
        String dateOfBirth;
        Integer creditLimit;
    }

    @Value
    @Builder
    @JsonDeserialize
    @AllArgsConstructor
    class GetCustomer implements CustomerCommand, PersistentEntity.ReplyType<GetCustomerReply> { }

    @SuppressWarnings("serial")
    @Value
    @Builder
    @JsonDeserialize
    @AllArgsConstructor
    class GetCustomerReply implements Jsonable {

        public final Optional<CustomerDto> dto;
    }
}
