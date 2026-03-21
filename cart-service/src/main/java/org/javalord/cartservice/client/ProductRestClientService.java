package org.javalord.cartservice.client;

import org.javalord.common.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface ProductRestClientService {

    @GetExchange(value = "/{productId}")
    ProductResponse getProduct(@PathVariable Long productId);

}
