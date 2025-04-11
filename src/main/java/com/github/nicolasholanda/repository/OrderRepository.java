package com.github.nicolasholanda.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import com.github.nicolasholanda.model.Order;
import com.github.nicolasholanda.model.dto.SaveOrderDTO;

@ApplicationScoped
public class OrderRepository {

    @Transactional
    public Order saveOrder(SaveOrderDTO saveOrderDTO) {
        Order order = new Order();

        order.setCustomerId(saveOrderDTO.customerId());
        order.setTotalAmount(saveOrderDTO.totalAmount());
        order.persist();

        return order;
    }
}
