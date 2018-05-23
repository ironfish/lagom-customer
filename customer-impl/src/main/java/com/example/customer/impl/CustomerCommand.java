package com.example.customer.impl;

import akka.Done;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

public interface CustomerCommand extends Jsonable {

    @Value
    @Builder
    @JsonDeserialize
    @AllArgsConstructor
    final class CreateCustomer implements CustomerCommand, PersistentEntity.ReplyType<Done> {
        String lastName;
        String firstName;
        String initial;
        String dateOfBirth;
        Integer creditLimit;
    }
}
