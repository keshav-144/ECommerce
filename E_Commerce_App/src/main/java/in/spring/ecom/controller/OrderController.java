package in.spring.ecom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import in.spring.ecom.entities.Cart;
import in.spring.ecom.entities.User;
import in.spring.ecom.services.CartService;
import in.spring.ecom.services.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private ProductService proService;

    @Autowired
    private CartService cartService;

    // Razorpay Keys from application.properties
    @Value("${razorpay.api.key}")
    private String razorpayKey;

    @Value("${razorpay.api.secret}")
    private String razorpaySecret;

    // ================= ORDER PAGE =================

    @GetMapping("/buyNow/{productId}")
    public String vieworderPage(@PathVariable int productId,
                                Model model,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        List<Cart> cartItems = cartService.getUserCart(user.getId());

        double totalOriginal = 0;
        double totalDiscounted = 0;

        for (Cart c : cartItems) {

            double original =
                    c.getProduct().getOriginalPrice() * c.getQuantity();

            double discounted =
                    c.getProduct().getDiscountedPrice() * c.getQuantity();

            totalOriginal += original;
            totalDiscounted += discounted;
        }

        double totalAmount = totalDiscounted;
        double totalSavings = totalOriginal - totalDiscounted;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalOriginal", totalOriginal);

        // FIXED
        model.addAttribute("totalDiscounted", totalDiscounted);

        model.addAttribute("totalSavings", totalSavings);
        model.addAttribute("totalAmount", totalAmount);

        // Pass Razorpay key to frontend
        model.addAttribute("razorpayKey", razorpayKey);

        return "order";
    }

    // ================= PLACE ORDER =================

    @PostMapping("/placeOrder")
    public String placeOrder(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        List<Cart> cartItems = cartService.getUserCart(user.getId());

        double totalOriginal = 0;
        double totalDiscounted = 0;

        for (Cart c : cartItems) {

            double original =
                    c.getProduct().getOriginalPrice() * c.getQuantity();

            double discounted =
                    c.getProduct().getDiscountedPrice() * c.getQuantity();

            totalOriginal += original;
            totalDiscounted += discounted;
        }

        double totalAmount = totalDiscounted;
        double totalSavings = totalOriginal - totalDiscounted;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalOriginal", totalOriginal);

        // FIXED
        model.addAttribute("totalDiscounted", totalDiscounted);

        model.addAttribute("totalSavings", totalSavings);
        model.addAttribute("totalAmount", totalAmount);

        model.addAttribute("razorpayKey", razorpayKey);

        return "order";
    }

    // ================= RAZORPAY PAYMENT ORDER =================

    @PostMapping("/createPaymentOrder")
    @ResponseBody
    public Map<String, Object> createPaymentOrder(
            @RequestBody Map<String, Object> data,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {

            User user = (User) session.getAttribute("loggedInUser");

            if (user == null) {

                response.put("status", "failed");
                response.put("message", "User not logged in");

                return response;
            }

            // FIXED
            double amountDouble =
                    Double.parseDouble(data.get("amount").toString());

            // Razorpay accepts amount in paise
            int amount = (int) Math.round(amountDouble * 100);

            RazorpayClient client =
                    new RazorpayClient(razorpayKey, razorpaySecret);

            JSONObject orderRequest = new JSONObject();

            orderRequest.put("amount", amount);

            orderRequest.put("currency", "INR");

            orderRequest.put(
                    "receipt",
                    "txn_" + System.currentTimeMillis()
            );

            Order razorpayOrder =
                    client.orders.create(orderRequest);

            response.put("status", "success");

            // FIXED
            response.put("orderId",
                    razorpayOrder.get("id"));

            response.put("amount",
                    razorpayOrder.get("amount"));

            response.put("currency",
                    razorpayOrder.get("currency"));

            response.put("key", razorpayKey);

        } catch (Exception e) {

            e.printStackTrace();

            response.put("status", "failed");

            response.put("message", e.getMessage());
        }

        return response;
    }
}