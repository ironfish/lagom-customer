package com.example.customer.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public class CustomerEntity extends PersistentEntity<CustomerCommand, CustomerEvent, CustomerState> {

    @Override
    public Behavior initialBehavior(Optional<CustomerState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(new CustomerState("Welcome", LocalDateTime.now().toString())));

        b.setCommandHandler(CustomerCommand.CreateCustomer.class, (cmd, ctx) ->
                ctx.thenPersist(new CustomerEvent.CustomerCreated(entityId(), cmd.message),
                        evt -> ctx.reply(Done.getInstance())));

        b.setEventHandler(CustomerEvent.CustomerCreated.class,
                evt -> new CustomerState(evt.message, LocalDateTime.now().toString()));

        b.setReadOnlyCommandHandler(CustomerCommand.GetCustomer.class,
                (cmd, ctx) -> ctx.reply(state().message + ", " + cmd.name + "!"));

        return b.build();
    }
}
