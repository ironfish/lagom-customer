package com.example.customer.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.annotation.concurrent.Immutable;

@SuppressWarnings("serial")
@Value
@Builder
@Immutable
@JsonDeserialize
@AllArgsConstructor
public class CustomerState implements CompressedJsonable {

    String lastName;
    String firstName;
    String initial;
    String dateOfBirth;
    Integer creditLimit;
    String timestamp;
}
