package com.example.customer.impl;

import akka.Done;
import akka.NotUsed;
import akka.japi.Pair;
import com.example.customer.api.CustomerEvent;
import com.example.customer.api.CustomerMessage;
import com.example.customer.api.CustomerService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.broker.TopicProducer;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import javax.inject.Inject;

public class CustomerServiceImpl implements CustomerService {

    private final PersistentEntityRegistry persistentEntityRegistry;

    @Inject
    public CustomerServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        persistentEntityRegistry.register(CustomerEntity.class);
    }

    @Override
    public ServiceCall<NotUsed, String> get_customer(String id) {
        return request -> {
            // Look up the hello world entity for the given ID.
            PersistentEntityRef<CustomerCommand> ref = persistentEntityRegistry.refFor(CustomerEntity.class, id);
            // Ask the entity the Hello command.
            return ref.ask(new CustomerCommand.GetCustomer(id));
        };
    }

    @Override
    public ServiceCall<CustomerMessage, Done> create_customer(String id) {
        return request -> {
            // Look up the hello world entity for the given ID.
            PersistentEntityRef<CustomerCommand> ref = persistentEntityRegistry.refFor(CustomerEntity.class, id);
            // Tell the entity to use the greeting message specified.
            return ref.ask(new CustomerCommand.CreateCustomer(request.message));
        };
    }

    @Override
    public Topic<CustomerEvent> customerEvents() {
        return TopicProducer.taggedStreamWithOffset(com.example.customer.impl.CustomerEvent.TAG.allTags(), (tag, offset) ->

                // Load the event stream for the passed in shard tag
                persistentEntityRegistry.eventStream(tag, offset).map(eventAndOffset -> {

                    // Now we want to convert from the persisted event to the published event.
                    // Although these two events are currently identical, in future they may
                    // change and need to evolve separately, by separating them now we save
                    // a lot of potential trouble in future.
                    com.example.customer.api.CustomerEvent eventToPublish;

                    if (eventAndOffset.first() instanceof com.example.customer.impl.CustomerEvent.CustomerCreated) {

                        com.example.customer.impl.CustomerEvent.CustomerCreated customerCreated =
                                (com.example.customer.impl.CustomerEvent.CustomerCreated) eventAndOffset.first();

                        eventToPublish = new com.example.customer.api.CustomerEvent.CustomerCreated(
                                customerCreated.getName(), customerCreated.getMessage()
                        );
                    } else {
                        throw new IllegalArgumentException("Unknown event: " + eventAndOffset.first());
                    }

                    // We return a pair of the translated event, and its offset, so that
                    // Lagom can track which offsets have been published.
                    return Pair.create(eventToPublish, eventAndOffset.second());
                })
        );
    }

}
