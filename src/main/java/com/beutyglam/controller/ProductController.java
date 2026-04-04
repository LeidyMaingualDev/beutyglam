package com.beutyglam.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beutyglam.dto.Product.ProductRequestDTO;
import com.beutyglam.dto.Product.ProductResponseDTO;
import com.beutyglam.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("No autorizado");
        }

        ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProductAll() {
        List<ProductResponseDTO> listProducts = productService.getProductAll();
        return ResponseEntity.status(HttpStatus.OK).body(listProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Integer id) {
        ProductResponseDTO productResponseDTO = productService.findProductById(id).orElse(null);

        if (productResponseDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductRequestDTO productRequestDTO, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("No autorizado");
        }

        ProductResponseDTO productResponseDTO = productService.updateProductById(id, productRequestDTO).orElse(null);

        if (productResponseDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteProduct(@PathVariable Integer id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("No autorizado");
        }
        
        ProductResponseDTO productResponseDTO = productService.deleteProductById(id).orElse(null);

        if (productResponseDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
