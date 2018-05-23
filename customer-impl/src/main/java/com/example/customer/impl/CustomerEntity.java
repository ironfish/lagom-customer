package com.example.customer.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public class CustomerEntity extends PersistentEntity<CustomerCommand, CustomerEvent, CustomerState> {

    @Override
    public Behavior initialBehavior(Optional<CustomerState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(new CustomerState(
                        "",
                        "",
                        "",
                        "",
                        0,
                        LocalDateTime.now().toString())));

        b.setCommandHandler(CustomerCommand.CreateCustomer.class, (cmd, ctx) ->
                ctx.thenPersist(new CustomerEvent.CustomerCreated(
                                cmd.getLastName(),
                                cmd.getFirstName(),
                                cmd.getInitial(),
                                cmd.getDateOfBirth(),
                                cmd.getCreditLimit(),
                                "todo"),
                        evt -> ctx.reply(Done.getInstance())));

        b.setEventHandler(CustomerEvent.CustomerCreated.class,
                evt -> new CustomerState(
                        evt.getLastName(),
                        evt.getFirstName(),
                        evt.getInitial(),
                        evt.getDateOfBirth(),
                        evt.getCreditLimit(),
                        LocalDateTime.now().toString())
        );

        return b.build();
    }
}
