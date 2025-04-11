package com.github.nicolasholanda.model.dto;

public record SaveOrderDTO(
    String customerId,
    double totalAmount
) {}