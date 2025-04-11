package com.github.nicolasholanda.routes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.github.nicolasholanda.model.dto.SaveOrderDTO;
import org.apache.camel.builder.RouteBuilder;
import com.github.nicolasholanda.service.OrderService;


@ApplicationScoped
public class OrderValidationRoute extends RouteBuilder {

    @Inject
    private OrderService orderService;

    @Override
    public void configure() {
        from("kafka:order-validation?brokers=localhost:9092")
                .routeId("order-validation-route")
                .log("Raw message: ${body}")
                .unmarshal().json(SaveOrderDTO.class)
                .log("Received order: ${body}")
                .choice()
                    .when(method(orderService, "isValidOrder"))
                        .log("Sending order to valid-order topic: ${body}")
                        .marshal().json()
                        .to("kafka:valid-order?brokers=localhost:9092")
                    .otherwise()
                        .log("Sending order to invalid-order topic: ${body}")
                        .marshal().json()
                        .to("kafka:invalid-order?brokers=localhost:9092");
    }
}
