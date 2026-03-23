package org.javalord.cartservice.cart;

import lombok.RequiredArgsConstructor;
import org.javalord.cartservice.cart.dto.CartAddRequest;
import org.javalord.cartservice.cart.dto.CartItemRequest;
import org.javalord.cartservice.client.ProductRestClientService;
import org.javalord.cartservice.util.AuthUtil;
import org.javalord.common.BusinessException;
import org.javalord.common.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final AuthUtil authUtil;
    private final CartRepository cartRepository;
    private final ProductRestClientService productRestClientService;
    private final CartItemRepository cartItemRepository;

    public void addToCart(CartAddRequest request) {
        long userId = authUtil.getCurrentUserId();
        Cart cart = getOrCreateCart(userId);

        for (CartItemRequest item : request.getCartItems()) {
            processCartItem(cart, item);
        }
    }

    private void processCartItem(Cart cart, CartItemRequest item) {
        ProductResponse productResponse = getProduct(item.getProductId());
        checkForEnoughQuantity(productResponse.getQuantity(), item.getQuantity());

        Optional<CartItem> checkCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), item.getProductId());

        if (checkCartItem.isPresent()) {
            CartItem cartItem = checkCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            cartItemRepository.save(cartItem);
        }
        else {
            CartItem newCartItem = CartItem
                    .builder()
                    .quantity(item.getQuantity())
                    .productId(productResponse.getId())
                    .cart(cart)
                    .build();
            cartItemRepository.save(newCartItem);
        }
    }

    private void checkForEnoughQuantity(int productQuantity, int requestedQuantity) {
        if (productQuantity < requestedQuantity) {
            throw new BusinessException("Request quantity is greater than item quantity");
        }
    }

    private ProductResponse getProduct(Long productId) {
        try {
            return productRestClientService.getProduct(productId);
        }
        catch (Exception e) {
            throw new BusinessException(e.getLocalizedMessage());
        }

    }

    private Cart getOrCreateCart(long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder().userId(userId).build()
                ));
    }
}
