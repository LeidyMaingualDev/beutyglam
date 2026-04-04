package com.beutyglam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beutyglam.dto.Product.ProductRequestDTO;
import com.beutyglam.dto.Product.ProductResponseDTO;
import com.beutyglam.entity.Product;
import com.beutyglam.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product();

        product.setProduct_name(productRequestDTO.getProduct_name());
        product.setPrice(productRequestDTO.getPrice());
        product.setDescription(productRequestDTO.getDescription());
        product.setStock(productRequestDTO.getStock());

        productRepository.save(product);

        ProductResponseDTO productResponse = new ProductResponseDTO();
        productResponse.setId(product.getId());
        productResponse.setProduct_name(product.getProduct_name());
        productResponse.setPrice(product.getPrice());
        productResponse.setDescription(product.getDescription());
        productResponse.setStock(product.getStock());

        return productResponse;
    }

    public List<ProductResponseDTO> getProductAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> ProductList = new ArrayList<>();

        for (Product p: products) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(p.getId());
            productResponseDTO.setProduct_name(p.getProduct_name());
            productResponseDTO.setPrice(p.getPrice());
            productResponseDTO.setDescription(p.getDescription());
            productResponseDTO.setStock(p.getStock());
            ProductList.add(productResponseDTO);
        }

        return ProductList;
    }

    public Optional<ProductResponseDTO> findProductById(Integer id) {
        Optional<Product> productOptional = productRepository .findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(product.getId());
            productResponseDTO.setProduct_name(product.getProduct_name());
            productResponseDTO.setPrice(product.getPrice());
            productResponseDTO.setDescription(product.getDescription());
            productResponseDTO.setStock(product.getStock());

            return Optional.of(productResponseDTO);
        } else {
            return Optional.empty();
        }
    }

    public Optional<ProductResponseDTO> updateProductById(Integer id, ProductRequestDTO productRequestDTO) {
        Optional<Product> productOptional = productRepository .findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setProduct_name(productRequestDTO.getProduct_name());
            product.setPrice(productRequestDTO.getPrice());
            product.setDescription(productRequestDTO.getDescription());
            product.setStock(productRequestDTO.getStock());

            Product productUpdate = productRepository.save(product);

            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(product.getId());
            productResponseDTO.setProduct_name(productUpdate.getProduct_name());
            productResponseDTO.setPrice(productUpdate.getPrice());
            productResponseDTO.setDescription(productUpdate.getDescription());
            productResponseDTO.setStock(productUpdate.getStock());

            return Optional.of(productResponseDTO);
        } else {
            return Optional.empty();
        }
    }

    public Optional<String> deleteProductById(Integer id) {
        Optional<Product> productOptional = productRepository .findById(id);

        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
            return Optional.of("Producto eliminado correctamente");
        } else {
            return Optional.empty();
        }
    }
}
