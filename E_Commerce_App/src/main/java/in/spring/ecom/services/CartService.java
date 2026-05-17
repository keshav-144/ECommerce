package in.spring.ecom.services;

import java.util.List;

import in.spring.ecom.entities.Cart;

public interface CartService {

    void addToCart(Long productId, int userId);

    List<Cart> getUserCart(int userId);
    
    void updateQuantity(Long cartId, int quantity);

    void removeCartItem(Long cartId);
    
    boolean productExistsInCart(Long productId, int userId);

}
