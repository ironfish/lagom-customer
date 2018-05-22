package com.example.customer.impl;

import com.example.customer.api.CustomerService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class CustomerModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(CustomerService.class, CustomerServiceImpl.class);
    }
}
