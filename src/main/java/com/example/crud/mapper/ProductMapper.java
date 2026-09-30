package com.example.crud.mapper;

import com.example.crud.dto.request.ProductRequest;
import com.example.crud.dto.response.ProductResponse;
import com.example.crud.entity.Product;
import org.springframework.stereotype.Component;

/**
 * Manual mapper untuk konversi antara Entity dan DTO.
 * Untuk project kecil-menengah, manual mapper lebih simple dan zero-dependency.
 */
@Component
public class ProductMapper {

    /**
     * Convert ProductRequest DTO → Product entity
     */
    public Product toEntity(ProductRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return product;
    }

    /**
     * Convert Product entity → ProductResponse DTO
     */
    public ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }

    /**
     * Update existing Product entity dari ProductRequest DTO
     */
    public void updateEntityFromRequest(ProductRequest request, Product product) {
        if (request == null) {
            return;
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
    }
}
