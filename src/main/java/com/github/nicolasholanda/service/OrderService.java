package com.github.nicolasholanda.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.github.nicolasholanda.model.Order;
import com.github.nicolasholanda.model.dto.SaveOrderDTO;
import com.github.nicolasholanda.repository.OrderRepository;

import static io.opentelemetry.api.internal.StringUtils.isNullOrEmpty;

@ApplicationScoped
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    public Order save(SaveOrderDTO saveOrderDTO) {
        return orderRepository.saveOrder(saveOrderDTO);
    }

    public boolean isValidOrder(SaveOrderDTO saveOrderDTO) {
        return !(isNullOrEmpty(saveOrderDTO.customerId()) ||
                saveOrderDTO.totalAmount() < 0);
    }
}
