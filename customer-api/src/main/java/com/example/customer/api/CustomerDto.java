package com.example.customer.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.annotation.concurrent.Immutable;

/**
 * The DTO for creating customer as payload to POST or streaming in.
 */
@Value
@Builder
@Immutable
@JsonDeserialize
@AllArgsConstructor
public class CustomerDto implements Jsonable {

    String lastName;
    String firstName;
    String initial;
    String dateOfBirth;
    Integer creditLimit;
    String timestamp;
}
