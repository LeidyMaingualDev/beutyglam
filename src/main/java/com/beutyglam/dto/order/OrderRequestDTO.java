package com.beutyglam.dto.order;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO{    
    private Integer productId;
    private Integer quantity;

    }
}
