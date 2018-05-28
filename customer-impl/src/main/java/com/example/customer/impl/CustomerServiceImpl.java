package com.example.customer.impl;

import akka.NotUsed;
import com.example.customer.api.CustomerCommand;
import com.example.customer.api.CustomerDto;
import com.example.customer.api.CustomerService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import javax.inject.Inject;
import java.util.UUID;

/**
 * Our one and only impl of Customer.
 */
public class CustomerServiceImpl implements CustomerService {

    private final PersistentEntityRegistry persistentEntityRegistry;

    @Inject
    public CustomerServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        persistentEntityRegistry.register(CustomerEntity.class);
    }

    @Override
    public ServiceCall<CustomerCommand.CreateCustomer, String> createCustomer() {
        return command -> {

            String customerId = UUID.randomUUID().toString();

            // Tell the entity to use the greeting message specified.
            return entityRef(customerId).ask(new CustomerCommand.CreateCustomer(
                    customerId,
                    command.getLastName(),
                    command.getFirstName(),
                    command.getInitial(),
                    command.getDateOfBirth(),
                    command.getCreditLimit())).thenApply( customer -> customerId);
        };
    }

    @Override
    public ServiceCall<NotUsed, CustomerDto> getCustomer(String customerId) {
        return request -> {
            return entityRef(customerId).ask(new CustomerCommand.GetCustomer()).thenApply(reply -> {
                if (reply.dto.isPresent())
                    return reply.dto.get();
                else
                    throw new NotFound("customer " + customerId + " not found");
            });
        };
    }

    /**
     * Create a new entity reference, generating a unique customer id.
     * @return
     */
    private PersistentEntityRef<CustomerCommand> entityRef(String customerId) {
        return persistentEntityRegistry.refFor(CustomerEntity.class, customerId);
    }
}
