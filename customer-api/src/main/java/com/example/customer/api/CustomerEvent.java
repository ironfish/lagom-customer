package com.example.customer.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Preconditions;
import lombok.Value;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = com.example.customer.api.CustomerEvent.CustomerCreated.class, name = "customer-created")
})
public interface CustomerEvent {

    String getName();

    @Value
    final class CustomerCreated implements CustomerEvent {
        public final String name;
        public final String message;

        @JsonCreator
        public CustomerCreated(String name, String message) {
            this.name = Preconditions.checkNotNull(name, "name");
            this.message = Preconditions.checkNotNull(message, "message");
        }

        public String getName() {
            return name;
        }

        public String getMessage() {
            return message;
        }
    }
}
