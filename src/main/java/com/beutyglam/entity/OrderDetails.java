package com.beutyglam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "`orderDetails`")
@Data
public class OrderDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "`fk_orderProduct_id`")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "fk_product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;
}
