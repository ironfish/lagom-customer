package com.example.customer.impl;

import akka.Done;
import com.example.customer.api.CreateCustomerDto;
import com.example.customer.api.CustomerService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
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
    public ServiceCall<CreateCustomerDto, Done> createCustomer() {
        return dto -> {
            // Tell the entity to use the greeting message specified.
            return newEntityRef().ask(new CustomerCommand.CreateCustomer(
                    dto.getLastName(),
                    dto.getFirstName(),
                    dto.getInitial(),
                    dto.getDateOfBirth(),
                    dto.getCreditLimit()));
        };
    }

    /**
     * Create a new entity reference, generating a unique customer id.
     * @return
     */
    private PersistentEntityRef<CustomerCommand> newEntityRef() {
        return persistentEntityRegistry.refFor(CustomerEntity.class, UUID.randomUUID().toString());
    }
}
