package com.shop.bhpt.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.bhpt.dto.CartItemRequest;
import com.shop.bhpt.entities.Cart;
import com.shop.bhpt.entities.CartItem;
import com.shop.bhpt.entities.Item;
import com.shop.bhpt.entities.User;
import com.shop.bhpt.exceptions.ResourceNotFoundException;
import com.shop.bhpt.repositories.CartRepository;
import com.shop.bhpt.repositories.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private  CartRepository cartRepository;
    private  ItemRepository itemRepository;
    private  UserService userService;

    public Cart getCurrentUserCart() {
        User user = userService.getCurrentUser();
        return cartRepository.findByUser(user)
                .orElseGet(() -> createNewCart(user));
    }

    public Cart addToCart(CartItemRequest request) {
        Cart cart = getCurrentUserCart();
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setSize(request.getSize());
        cartItem.setColor(request.getColor());
        cartItem.setPrice(item.getPrice() * request.getQuantity());

        cart.getCartItems().add(cartItem);
        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }

    public Cart updateCartItem(Long cartItemId, CartItemRequest request) {
        Cart cart = getCurrentUserCart();
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartItem.setQuantity(request.getQuantity());
        cartItem.setSize(request.getSize());
        cartItem.setColor(request.getColor());
        cartItem.setPrice(cartItem.getItem().getPrice() * request.getQuantity());

        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }

    public Cart removeFromCart(Long cartItemId) {
        Cart cart = getCurrentUserCart();
        cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));
        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }

    public void clearCart() {
        Cart cart = getCurrentUserCart();
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }

    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0);
        return cartRepository.save(cart);
    }

    private void updateCartTotalPrice(Cart cart) {
        double total = cart.getCartItems().stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
        cart.setTotalPrice(total);
    }
} 