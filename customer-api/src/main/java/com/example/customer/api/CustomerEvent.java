package com.example.customer.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.annotation.concurrent.Immutable;

/**
 * Wrapping interface for all customer event impls.
 */
public interface CustomerEvent extends Jsonable, AggregateEvent<CustomerEvent> {

    AggregateEventShards<CustomerEvent> TAG =
            AggregateEventTag.sharded(CustomerEvent.class, 4);

    @Override
    default AggregateEventTagger<CustomerEvent> aggregateTag() {
        return TAG;
    }

    @SuppressWarnings("serial")
    @Value
    @Builder
    @Immutable
    @JsonDeserialize
    @AllArgsConstructor
    class CustomerCreated implements CustomerEvent {

        String lastName;
        String firstName;
        String initial;
        String dateOfBirth;
        Integer creditLimit;
        String timestamp;
    }
}
