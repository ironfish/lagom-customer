package com.example.customer.stream.impl;

import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.example.customer.api.CustomerService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.example.customer.stream.api.StreamService;

import javax.inject.Inject;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Implementation of the HelloString.
 */
public class StreamServiceImpl implements StreamService {

  private final CustomerService customerService;
  private final StreamRepository repository;

  @Inject
  public StreamServiceImpl(CustomerService customerService, StreamRepository repository) {
    this.customerService = customerService;
    this.repository = repository;
  }

  @Override
  public ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> directStream() {
    return customers -> completedFuture(
      customers.mapAsync(8, name ->  customerService.get_customer(name).invoke()));
  }

  @Override
  public ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> autonomousStream() {
    return customers -> completedFuture(
            customers.mapAsync(8, name -> repository.getMessage(name).thenApply( message ->
            String.format("%s, %s!", message.orElse("Hello"), name)
        ))
    );
  }
}
