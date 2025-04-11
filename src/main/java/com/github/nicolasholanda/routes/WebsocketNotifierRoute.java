package com.github.nicolasholanda.routes;

import org.apache.camel.builder.RouteBuilder;

public class WebsocketNotifierRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("vertx-websocket:/orders-notification")
                .log("WebSocket connected: ${body}");
    }
}
