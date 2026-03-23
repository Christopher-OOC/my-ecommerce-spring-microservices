package org.javalord.cartservice.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartAddRequest {

    private List<CartItemRequest> cartItems;

}
