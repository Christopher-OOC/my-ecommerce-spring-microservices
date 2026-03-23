package org.javalord.cartservice.cart;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.javalord.cartservice.cart.dto.CartAddRequest;
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
//    @PreAuthorize("isAuthenticated()")
    public RestResponse<String> addToCart(@RequestBody CartAddRequest request, HttpServletRequest httpRequest) {

//        httpRequest.getHeader("Authorization");

        cartService.addToCart(request);

        RestResponse<String> response = new RestResponse<>(
                Status.SUCCESS,
                "Cart items added",
                "Added"
        );

        return response;
    }

}
