package org.javalord.cartservice.cart;

import lombok.RequiredArgsConstructor;
import org.javalord.cartservice.cart.dto.AddToCartRequest;
import org.javalord.cartservice.client.ProductRestClientService;
import org.javalord.cartservice.client.UserRestClientService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceRefactor {

    private final CartRepository cartRepository;
    private final ProductRestClientService productRestClientService;
    private final UserRestClientService userRestClientService;
    private final CartItemRepository cartItemRepository;

    public void addToCart(AddToCartRequest request) {

    }
}
