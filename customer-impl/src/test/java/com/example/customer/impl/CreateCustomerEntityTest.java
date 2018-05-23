//package com.example.customer.impl;
//
//import akka.Done;
//import akka.actor.ActorSystem;
//import akka.testkit.JavaTestKit;
//import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.util.Collections;
//
//import static org.junit.Assert.assertEquals;
//
//public class CreateCustomerEntityTest {
//    static ActorSystem system;
//
//    @BeforeClass
//    public static void setup() {
//        system = ActorSystem.create("CreateCustomerEntityTest");
//    }
//
//    @AfterClass
//    public static void teardown() {
//        JavaTestKit.shutdownActorSystem(system);
//        system = null;
//    }
//
//    @Test
//    public void testCustomer() {
//        PersistentEntityTestDriver<CustomerCommand, CustomerEvent, CustomerState> driver =
//                new PersistentEntityTestDriver<>(system, new CustomerEntity(), "cust-1");
//
//        PersistentEntityTestDriver.Outcome<CustomerEvent, CustomerState> outcome1 = driver.run(new CustomerCommand.GetCustomer("Sean"));
//        assertEquals("Welcome, Sean!", outcome1.getReplies().get(0));
//        assertEquals(Collections.emptyList(), outcome1.issues());
//
//        PersistentEntityTestDriver.Outcome<CustomerEvent, CustomerState> outcome2 = driver.run(new CustomerCommand.CreateCustomer("Hi"),
//                new CustomerCommand.GetCustomer("Bob"));
//        assertEquals(1, outcome2.events().size());
//        assertEquals(new CustomerEvent.CustomerCreated("cust-1", "Hi"), outcome2.events().get(0));
//        assertEquals("Hi", outcome2.state().message);
//        assertEquals(Done.getInstance(), outcome2.getReplies().get(0));
//        assertEquals("Hi, Bob!", outcome2.getReplies().get(1));
//        assertEquals(2, outcome2.getReplies().size());
//        assertEquals(Collections.emptyList(), outcome2.issues());
//    }
//}
