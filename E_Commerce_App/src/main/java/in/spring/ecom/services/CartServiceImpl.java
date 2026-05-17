package in.spring.ecom.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.spring.ecom.entities.Cart;
import in.spring.ecom.entities.Product;
import in.spring.ecom.entities.User;
import in.spring.ecom.repositories.CartRepository;
import in.spring.ecom.repositories.ProductRepository;
import in.spring.ecom.repositories.UserRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

	@Override
	public void addToCart(Long productId, int userId) {
		User user =
                userRepository.findById(userId).orElse(null);
        Product product =
                productRepository.findById(productId).orElse(null);
        if(user == null || product == null)
            return;
        Cart existingCart =
                cartRepository.findByUserAndProduct(user, product);
        if(existingCart != null)
        {
            return;
        }
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(1);
        cartRepository.save(cart);
	}
	
    @Override
    public List<Cart> getUserCart(int userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void removeCartItem(Long cartId) {

        cartRepository.deleteById(cartId);

    }

	@Override
	public void updateQuantity(Long cartId, int quantity) {
		 Cart cart = cartRepository.findById(cartId).orElse(null);

		 if (cart == null)
		        return;

		    if (quantity <= 0) {

		        cartRepository.delete(cart);

		    } else {

		        cart.setQuantity(quantity);

		        cartRepository.save(cart);
		   }
	}

	@Override
	public boolean productExistsInCart(Long productId, int userId) {
		Cart cart =
		        cartRepository.
		        findByUserIdAndProductId(userId, productId);

		    return cart != null;
	}
 }
