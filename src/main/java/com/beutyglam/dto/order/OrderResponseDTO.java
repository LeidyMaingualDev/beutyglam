package com.beutyglam.dto.order;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderResponseDTO {
    
    private Long orderId;
	private String username;
	private LocalDate orderDate;
	private Double total;
	private String message;  
}
