package org.javalord.cartservice.cart;

import lombok.RequiredArgsConstructor;
import org.javalord.cartservice.cart.dto.AddToCartRequest;
import org.javalord.common.RestResponse;
import org.javalord.common.Status;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public RestResponse<String> addToCart(@RequestBody AddToCartRequest request) {
        cartService.addToCart(request);

        RestResponse<String> response = new RestResponse<>(
                Status.SUCCESS,
                "Cart items added",
                "Added"
        );

        return response;
    }

}
