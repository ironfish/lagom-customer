package com.example.customer.api;

import akka.NotUsed;
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
    ServiceCall<CustomerCommand.CreateCustomer, String> createCustomer();

    /**
     * Get customer by id.
     * Example: curl http://localhost:9000/customer/customers/someuuid
     */
    ServiceCall<NotUsed, CustomerDto> getCustomer(String customerId);

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("customer").withCalls(
                restCall(Method.POST, "/customers/", this::createCustomer),
                restCall(Method.GET, "/customers/:id", this::getCustomer)
        ).withAutoAcl(true);
        // @formatter:on
    }
}
