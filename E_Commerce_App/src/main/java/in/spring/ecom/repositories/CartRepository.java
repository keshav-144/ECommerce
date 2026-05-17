package in.spring.ecom.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import in.spring.ecom.entities.Cart;
import in.spring.ecom.entities.Product;
import in.spring.ecom.entities.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(int userId);
    
    Cart findByUserIdAndProductId(int userId, Long productId);
    Cart findByUserAndProduct(User user, Product product);
    
    void deleteByProductId(Long productId);

}
