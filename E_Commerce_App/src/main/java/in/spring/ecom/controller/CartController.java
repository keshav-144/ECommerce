package in.spring.ecom.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.spring.ecom.entities.Cart;
import in.spring.ecom.entities.User;
import in.spring.ecom.repositories.ProductRepository;
import in.spring.ecom.services.CartService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

	@Autowired
	private CartService cService;
	@Autowired
	private ProductRepository pRepo;

	@GetMapping("/cart")
	public String viewCart(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null) {
			return "redirect:/login";
		}
		List<Cart> cartItems = cService.getUserCart(user.getId());
		double totalOriginal = 0;
		double totalDiscounted = 0;
		for (Cart c : cartItems) {
			double original = c.getProduct().getOriginalPrice() * c.getQuantity();
			double discounted = c.getProduct().getDiscountedPrice() * c.getQuantity();

			totalOriginal += original;
			totalDiscounted += discounted;
		}
		double totalAmount = totalDiscounted;
		double totalSavings = totalOriginal - totalDiscounted;
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalOriginal", totalOriginal);
		model.addAttribute("totalDiscounted", totalSavings);
		model.addAttribute("totalAmount", totalAmount);
		return "cart";
	}

	@GetMapping("/cart/add/{productId}")
	public String addToCart(
			@PathVariable Long productId, 
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		User user = (User) session.getAttribute("loggedInUser");

		if (user == null) {
			return "redirect:/login";
		}
		
		boolean alreadyExists =
				cService.productExistsInCart(productId, user.getId());
		if (!alreadyExists)
        {
            cService.addToCart(productId,
                               user.getId());
            redirectAttributes.addFlashAttribute(
                    "successMsg",
                    "Item added successfully"
            );
            return "redirect:/cart";
           
        }
		else {
			redirectAttributes.addFlashAttribute(
	                "errorMsg",
	                "Item already present in cart"
	        );
			 return "redirect:/cart";
		}	
	}

	@GetMapping("/cart/update/{cartId}/{qty}")
	public String updateCart(@PathVariable Long cartId, @PathVariable int qty) {
		cService.updateQuantity(cartId, qty);
		return "redirect:/cart";
	}

	@GetMapping("/cart/remove/{cartId}")
	public String removeItem(@PathVariable Long cartId) {

		cService.removeCartItem(cartId);

		return "redirect:/cart";
	}
}
