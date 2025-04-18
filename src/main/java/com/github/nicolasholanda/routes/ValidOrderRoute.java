package com.github.nicolasholanda.routes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.github.nicolasholanda.model.Order;
import com.github.nicolasholanda.model.dto.SaveOrderDTO;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import com.github.nicolasholanda.service.OrderService;

@ApplicationScoped
public class ValidOrderRoute extends RouteBuilder {

    @Inject
    private OrderService orderService;

    @Override
    public void configure() throws Exception {
        errorHandler(
            deadLetterChannel("kafka:dlq-orders?brokers=localhost:9092")
                    .maximumRedeliveries(3)
                    .redeliveryDelay(2000)
                    .retryAttemptedLogLevel(LoggingLevel.WARN)
        );

        from("kafka:valid-order?brokers=localhost:9092")
                .routeId("valid-order-route")
                .log("Raw message: ${body}")
                .unmarshal().json(SaveOrderDTO.class)
                .log("Received order: ${body}")
                .delay(2000)
                .process(exchange -> {
                    SaveOrderDTO orderDTO = exchange.getIn().getBody(SaveOrderDTO.class);
                    Order persistedOrder = orderService.save(orderDTO);
                    exchange.getIn().setBody(persistedOrder);
                })
                .log("Persisted order: ${body}")
                .to("vertx-websocket:/orders-notification?sendToAll=true");
    }
}
