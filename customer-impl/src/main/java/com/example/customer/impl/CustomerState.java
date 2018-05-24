package com.example.customer.impl;

import com.example.customer.api.CustomerDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.annotation.concurrent.Immutable;
import java.util.Optional;

@SuppressWarnings("serial")
@Value
@Builder
@Immutable
@JsonDeserialize
@AllArgsConstructor
public class CustomerState implements CompressedJsonable {

    Optional<CustomerDto> dto;

    /**
     * Initial, empty customer state.
     */
    public static CustomerState empty() {

        return new CustomerState(Optional.empty());
    }
}
