package com.beutyglam.dto.Product;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Integer id;
    private String product_name;
    private Double price;
    private String description;
    private Integer stock;
}
