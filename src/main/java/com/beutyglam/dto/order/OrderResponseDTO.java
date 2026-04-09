package com.beutyglam.dto.order;

import java.time.LocalDate;
import java.util.List;


import lombok.Data;

@Data
public class OrderResponseDTO {
    
    private Long orderId;
	private String username;
	private LocalDate orderDate;
	private Double total;
	private String message;  
	private List<OrderItemResponseDTO> items;

	@Data
	public static class OrderItemResponseDTO{
		private String productName;
		private Integer quantity;
		private Double price;
	}
}
