package com.store_api.controllers;

import com.store_api.dtos.CartDto;
import com.store_api.dtos.CartItemDto;
import com.store_api.exceptions.CartNotFoundException;
import com.store_api.exceptions.ProductNotFoundException;
import com.store_api.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto); // STATUS: 201 Created
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto.CartItemResponse> addToCart(@PathVariable UUID cartId,
            @RequestBody CartItemDto.AddItemToCartRequest request) {
        var cartItemDto = cartService.addItemToCart(cartId, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto); // STATUS: 201 Created
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId) {
        return cartService.getCart(cartId);
    }

    @PatchMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto.CartItemResponse> updateItems(@PathVariable UUID cartId,
            @PathVariable Long productId, @Valid @RequestBody CartItemDto.UpdateCartItemRequest request) {
        var response = cartService.updateCartItem(cartId, productId, request.getQuantity());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeItem(@PathVariable UUID cartId, @PathVariable Long productId) {
        cartService.removeItemFromCart(cartId, productId);
        return ResponseEntity.noContent().build(); // STATUS: 204 No Content
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build(); // STATUS: 204 No Content
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found."));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found in the cart."));
    }
}
