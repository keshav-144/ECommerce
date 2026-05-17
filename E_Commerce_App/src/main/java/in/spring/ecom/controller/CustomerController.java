package in.spring.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import in.spring.ecom.entities.User;
import in.spring.ecom.services.UserService;

@Controller
public class CustomerController {
	
	@Autowired
	private UserService uService;
	@GetMapping("/customer")
    public String viewCustomers(Model model,
    		@RequestParam(defaultValue = "1") int pageNo) {
        int pageSize = 4;
		Page<User> page = uService.findPaginated(pageNo, pageSize);

        model.addAttribute("users", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "customer";
    }
	
    @GetMapping("/ban/{id}")
    public String banUser(@PathVariable int id) {
        uService.banUser(id);
        return "redirect:/customer";
    }

    @GetMapping("/unban/{id}")
    public String unbanUser(@PathVariable int id) {
        uService.unbanUser(id);
        return "redirect:/customer";
    }
}
