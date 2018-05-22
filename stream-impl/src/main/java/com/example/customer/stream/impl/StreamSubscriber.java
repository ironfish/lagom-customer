package com.example.customer.stream.impl;

import akka.Done;
import akka.stream.javadsl.Flow;
import com.example.customer.api.CustomerEvent;
import com.example.customer.api.CustomerService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class StreamSubscriber {

  @Inject
  public StreamSubscriber(CustomerService customerService, StreamRepository repository) {
    // Create a subscriber
      customerService.customerEvents().subscribe()
      // And subscribe to it with at least once processing semantics.
      .atLeastOnce(
        // Create a flow that emits a Done for each message it processes
        Flow.<CustomerEvent>create().mapAsync(1, event -> {

          if (event instanceof CustomerEvent.CustomerCreated) {
            CustomerEvent.CustomerCreated customerCreated = (CustomerEvent.CustomerCreated) event;
            // Update the message
            return repository.updateMessage(customerCreated.getName(), customerCreated.getMessage());

          } else {
            // Ignore all other events
            return CompletableFuture.completedFuture(Done.getInstance());
          }
        })
      );

  }
}
