package com.javalord.product_service.product;

import com.javalord.product_service.dto.CreateProductRequest;
import org.javalord.common.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {


    public Product mapCreateRequestToProduct(CreateProductRequest request) {
        return Product
                .builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .build();
    }

    public ProductResponse mapProductToResponse(Product product) {
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .build();
    }
}
