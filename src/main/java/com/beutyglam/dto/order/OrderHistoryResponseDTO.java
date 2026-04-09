package com.beutyglam.dto.order;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"orderId", "orderDate", "total", "items"})
public class OrderHistoryResponseDTO {
    private Long orderId;
    private LocalDate orderDate;
    private Double total;
    private List<OrderItemDTO> items;
  
    @Data
    public static class OrderItemDTO{
        private String productName;
        private Integer quantity;
        private Double price;
    }
}
