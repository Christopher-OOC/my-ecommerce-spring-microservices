package com.javalord.product_service.product;

import com.javalord.product_service.dto.CreateProductRequest;
import com.javalord.product_service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.javalord.common.RestResponse;
import org.javalord.common.Status;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public RestResponse<String> createProduct(@RequestBody CreateProductRequest request) {
        productService.createProduct(request);

        RestResponse<String> response = new RestResponse<>(
                Status.SUCCESS,
                "Product created successfully",
                "Product created successfully"
        );

        return response;
    }

    @GetMapping
    public RestResponse<List<ProductResponse>> getProducts() {
        List<ProductResponse> products = productService.getProducts();

        RestResponse<List<ProductResponse>> response = new RestResponse<>(
                Status.SUCCESS,
                "Product created successfully",
                products
        );

        return response;
    }

    @GetMapping(value = "/{productId}")
    public RestResponse<ProductResponse> getProduct(@PathVariable long productId) {
        ProductResponse product = productService.getProduct(productId);

        RestResponse<ProductResponse> response = new RestResponse<>(
                Status.SUCCESS,
                "Product created successfully",
                product
        );

        return response;
    }


}
