package com.example.customer.impl;

import com.example.customer.api.CustomerMessage;
import com.example.customer.api.CustomerService;
import org.junit.Test;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.withServer;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

public class CustomerServiceTest {
    @Test
    public void shouldStoreCustomer() throws Exception {
        withServer(defaultSetup().withCassandra(), server -> {
            CustomerService service = server.client(CustomerService.class);

            String msg1 = service.get_customer("Alice").invoke().toCompletableFuture().get(5, SECONDS);
            assertEquals("Welcome, Alice!", msg1);

            service.create_customer("Alice").invoke(new CustomerMessage("Hi")).toCompletableFuture().get(5, SECONDS);
            String msg2 = service.get_customer("Alice").invoke().toCompletableFuture().get(5, SECONDS);
            assertEquals("Hi, Alice!", msg2);

            String msg3 = service.get_customer("Bob").invoke().toCompletableFuture().get(5, SECONDS);
            assertEquals("Welcome, Bob!", msg3);
        });
    }

}
