package com.github.nicolasholanda.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import com.github.nicolasholanda.model.dto.SaveOrderDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/orders")
public class OrderResource {

    @Inject
    @Channel("order-validation")
    Emitter<SaveOrderDTO> orderValidationEmitter;

    @POST
    public Response save(SaveOrderDTO saveOrderDTO) {
        orderValidationEmitter.send(saveOrderDTO);
        return Response.accepted().entity("Order received.").build();
    }
}
