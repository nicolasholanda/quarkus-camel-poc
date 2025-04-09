package repository;

import jakarta.enterprise.context.ApplicationScoped;
import model.Order;
import model.dto.SaveOrderDTO;

@ApplicationScoped
public class OrderRepository {

    public Order saveOrder(SaveOrderDTO saveOrderDTO) {
        Order order = new Order();

        order.setCustomerId(saveOrderDTO.customerId());
        order.setTotalAmount(saveOrderDTO.totalAmount());
        order.persist();

        return order;
    }
}
