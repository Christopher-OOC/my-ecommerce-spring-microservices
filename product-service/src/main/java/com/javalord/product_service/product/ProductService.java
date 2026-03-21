package com.javalord.product_service.product;

import com.javalord.product_service.dto.CreateProductRequest;
import com.javalord.product_service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javalord.common.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public void createProduct(CreateProductRequest request) {
        Product product = this.productMapper.mapCreateRequestToProduct(request);

        log.info("Creating product {}", product);

        productRepository.save(product);
    }

    public List<ProductResponse> getProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(this.productMapper::mapProductToResponse)
                .toList();
    }

    public ProductResponse getProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Product not found"));

        return this.productMapper.mapProductToResponse(product);
    }
}
