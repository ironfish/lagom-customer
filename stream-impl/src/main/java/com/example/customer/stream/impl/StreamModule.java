package com.example.customer.stream.impl;

import com.example.customer.api.CustomerService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.example.customer.stream.api.StreamService;

public class StreamModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindService(StreamService.class, StreamServiceImpl.class);
    bindClient(CustomerService.class);
    bind(StreamSubscriber.class).asEagerSingleton();
  }
}
