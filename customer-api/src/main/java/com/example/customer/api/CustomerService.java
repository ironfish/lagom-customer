package com.example.customer.api;

import akka.Done;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.*;

/**
 * The customer service interface for working with customer.
 */
public interface CustomerService extends Service {

    /**
     * Example: curl http://localhost:9000/customer/customers
     * {"lastName":"Doe", "firstName": "John", "initial": "D", "dateOfBirth": "1970-01-01", "creditLimit": 2000.0}
     */
    ServiceCall<CreateCustomerDto, Done> createCustomer();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("customer").withCalls(
                restCall(Method.POST, "/customers/", this::createCustomer)
        ).withAutoAcl(true);
        // @formatter:on
    }
}
