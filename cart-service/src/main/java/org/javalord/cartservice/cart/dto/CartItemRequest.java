package org.javalord.cartservice.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CartItemRequest {

    private Long productId;
    private int quantity;

}
