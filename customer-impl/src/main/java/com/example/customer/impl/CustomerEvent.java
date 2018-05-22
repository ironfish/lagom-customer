package com.example.customer.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

public interface CustomerEvent extends Jsonable, AggregateEvent<CustomerEvent> {

    AggregateEventShards<CustomerEvent> TAG =
            AggregateEventTag.sharded(CustomerEvent.class, 4);

    @Override
    default AggregateEventTagger<CustomerEvent> aggregateTag() {
        return TAG;
    }

    @SuppressWarnings("serial")
    @Value
    @JsonDeserialize
    public final class CustomerCreated implements CustomerEvent {

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
