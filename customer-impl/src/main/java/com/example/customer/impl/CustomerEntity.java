package com.example.customer.impl;

import com.example.customer.api.CustomerCommand;
import com.example.customer.api.CustomerDto;
import com.example.customer.api.CustomerEvent;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public class CustomerEntity extends PersistentEntity<CustomerCommand, CustomerEvent, CustomerState> {

    @Override
    public Behavior initialBehavior(Optional<CustomerState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(
                snapshotState.orElse(CustomerState.empty()));

        b.setCommandHandler(CustomerCommand.CreateCustomer.class, (cmd, ctx) ->
                ctx.thenPersist(new CustomerEvent.CustomerCreated(
                                cmd.getLastName(),
                                cmd.getFirstName(),
                                cmd.getInitial(),
                                cmd.getDateOfBirth(),
                                cmd.getCreditLimit(),
                                LocalDateTime.now().toString()),
                        evt -> ctx.reply(cmd.getCustomerId())));

        b.setEventHandler(CustomerEvent.CustomerCreated.class,
                evt -> new CustomerState(Optional.of(new CustomerDto(
                        evt.getLastName(),
                        evt.getFirstName(),
                        evt.getInitial(),
                        evt.getDateOfBirth(),
                        evt.getCreditLimit(),
                        evt.getTimestamp())))
        );

        b.setReadOnlyCommandHandler(CustomerCommand.GetCustomer.class, (cmd, ctx) -> {
            ctx.reply(new CustomerCommand.GetCustomerReply(state().getDto()));
        });

        return b.build();
    }
}
