package org.javalord.cartservice.cart;


import lombok.RequiredArgsConstructor;
import org.javalord.cartservice.cart.dto.AddToCartRequest;
import org.javalord.cartservice.cart.dto.CartItemRequest;
import org.javalord.cartservice.client.ProductRestClientService;
import org.javalord.cartservice.client.UserRestClientService;
import org.javalord.common.BusinessException;
import org.javalord.common.ProductResponse;
import org.javalord.common.UserResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRestClientService productRestClientService;
    private final UserRestClientService userRestClientService;
    private final CartItemRepository cartItemRepository;

    public void addToCart(AddToCartRequest request) {
        UserResponse user;
        try {
            user = userRestClientService.getUser(request.getUserId());
        }
        catch (Exception e) {
            if (e.getLocalizedMessage().contains("USER")) {
                throw new BusinessException(e.getLocalizedMessage());
            }

            throw new BusinessException("Error adding to cart");
        }

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> cartRepository.save(
                        Cart.builder().userId(user.getId()).build()
                ));

        for (CartItemRequest item : request.getItems()) {
            try {
                ProductResponse product = productRestClientService.getProduct(item.getProductId());

                if (product.getQuantity() < item.getQuantity()) {
                    throw new BusinessException("product stock is lower than item quantity");
                }

                Optional<CartItem> checkCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());
                if (checkCartItem.isPresent()) {
                    CartItem existing = checkCartItem.get();
                    existing.setQuantity(checkCartItem.get().getQuantity() + item.getQuantity());
                    cartItemRepository.save(existing);
                }
                else {
                    CartItem newCartItem = CartItem
                            .builder()
                            .quantity(item.getQuantity())
                            .productId(product.getId())
                            .cart(cart)
                            .build();
                    cartItemRepository.save(newCartItem);
                }


            }
            catch (Exception e) {
                throw new BusinessException(e.getLocalizedMessage());
            }
        }
    }
}
