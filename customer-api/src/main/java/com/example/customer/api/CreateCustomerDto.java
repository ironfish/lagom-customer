package com.example.customer.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.annotation.concurrent.Immutable;

/**
 * The DTO for creating customer as payload to POST or streaming in.
 */
@Value
@Builder
@Immutable
@JsonDeserialize
@AllArgsConstructor
public class CreateCustomerDto {

    String lastName;
    String firstName;
    String initial;
    String dateOfBirth;
    Integer creditLimit;
}
