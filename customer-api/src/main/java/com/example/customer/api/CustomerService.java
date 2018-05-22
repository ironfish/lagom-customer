package com.example.customer.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.api.broker.kafka.KafkaProperties;

import static com.lightbend.lagom.javadsl.api.Service.*;

public interface CustomerService extends Service {

    /**
     * Example: curl http://localhost:9000/api/get_customer/Alice
     */
    ServiceCall<NotUsed, String> get_customer(String id);


    /**
     * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
     * "hi_alice"}' http://localhost:9000/api/create_customer/Alice
     */
    ServiceCall<CustomerMessage, Done> create_customer(String id);

    /**
     * This gets published to Kafka.
     */
    Topic<CustomerEvent> customerEvents();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("customer").withCalls(
                pathCall("/api/customer/:id", this::get_customer),
                pathCall("/api/customer/:id", this::create_customer)
        ).withTopics(
                topic("customer-events", this::customerEvents)
                        // Kafka partitions messages, messages within the same partition will
                        // be delivered in order, to ensure that all messages for the same user
                        // go to the same partition (and hence are delivered in order with respect
                        // to that user), we configure a partition key strategy that extracts the
                        // name as the partition key.
                        .withProperty(KafkaProperties.partitionKeyStrategy(), CustomerEvent::getName)
        ).withAutoAcl(true);
        // @formatter:on
    }
}
