package com.example.customer.impl;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

public interface CustomerCommand extends Jsonable {

    @SuppressWarnings("serial")
    @Value
    @JsonDeserialize
    final class CreateCustomer implements CustomerCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
        public final String message;

        @JsonCreator
        public CreateCustomer(String message) {
            this.message = Preconditions.checkNotNull(message, "message");
        }
    }

    @SuppressWarnings("serial")
    @Value
    @JsonDeserialize
    final class GetCustomer implements CustomerCommand, PersistentEntity.ReplyType<String> {

        public final String name;

        @JsonCreator
        public GetCustomer(String name) {
            this.name = Preconditions.checkNotNull(name, "name");
        }
    }

}
