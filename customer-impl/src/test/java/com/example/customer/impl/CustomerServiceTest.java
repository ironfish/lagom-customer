package com.example.customer.impl;

import com.example.customer.api.CustomerCommand;
import com.example.customer.api.CustomerDto;
import com.example.customer.api.CustomerService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.withServer;
import static java.util.concurrent.TimeUnit.SECONDS;

public class CustomerServiceTest {
    @Test
    public void shouldStoreCustomer() throws Exception {
        withServer(defaultSetup().withCassandra(), server -> {

            CustomerService service = server.client(CustomerService.class);

            String customerId = service.createCustomer().invoke(new CustomerCommand.CreateCustomer("",
                    "Doe", "John","D","1970-01-01", 2000)
            ).toCompletableFuture().get(5, SECONDS);


            CustomerDto customer = service.getCustomer(customerId).invoke().
                    toCompletableFuture().get(5, SECONDS);

            assertEquals("Doe", customer.getLastName());
            assertEquals("John", customer.getFirstName());
            assertEquals("D", customer.getInitial());
            assertEquals("1970-01-01", customer.getDateOfBirth());
            assertEquals("2000", customer.getCreditLimit().toString());
        });
    }
}
