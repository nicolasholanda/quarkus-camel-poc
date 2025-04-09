package routes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.dto.SaveOrderDTO;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.OrderRepository;

import static io.opentelemetry.api.internal.StringUtils.isNullOrEmpty;


@ApplicationScoped
public class OrderValidationRoute extends RouteBuilder {

    @Inject
    private OrderRepository orderRepository;

    Logger logger = LoggerFactory.getLogger(OrderValidationRoute.class);

    @Override
    public void configure() {
        from("kafka:order-validation?brokers=localhost:9092")
                .routeId("order-validation-route")
                .unmarshal().json(SaveOrderDTO.class)
                .process(exchange -> {
                    SaveOrderDTO saveOrderDTO = exchange.getIn().getBody(SaveOrderDTO.class);
                    logger.info("Received order for validation: {}", saveOrderDTO.customerId());
                    processOrder(saveOrderDTO);
                });
    }

    private void processOrder(SaveOrderDTO saveOrderDTO) {
        if(!isValidOrder(saveOrderDTO)) {
            logger.warn("This order is not valid: {}", saveOrderDTO);
            return;
        }

        logger.info("Persisting valid order: {}", saveOrderDTO);
        orderRepository.saveOrder(saveOrderDTO);
    }

    private boolean isValidOrder(SaveOrderDTO saveOrderDTO) {
        return !(isNullOrEmpty(saveOrderDTO.customerId()) ||
                saveOrderDTO.totalAmount() < 0);
    }
}
