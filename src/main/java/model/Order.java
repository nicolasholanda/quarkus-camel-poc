package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Order extends PanacheEntity {

    private String customerId;
    private double totalAmount;
    private LocalDateTime createdAt = LocalDateTime.now();
}


