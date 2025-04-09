package model.dto;

public record SaveOrderDTO(
    String customerId,
    double totalAmount
) {}