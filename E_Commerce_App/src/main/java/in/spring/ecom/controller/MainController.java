package in.spring.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.spring.ecom.entities.User;
import in.spring.ecom.services.ProductService;
import in.spring.ecom.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	@Autowired
    private ProductService proServ;
	
	@Autowired
	private UserService userServ;

    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("products", proServ.getAllProduct());
        return "home";
    }

    @GetMapping("/home")
    public String homeRedirect()
    {
        return "redirect:/";
    }
    
    @GetMapping("/login")
    public String login(Model model)
    {
    	model.addAttribute("user",new User());
    	return "login";
    }
    
    @PostMapping("/loginForm")
	public String submitLogin(@ModelAttribute("user") User user,Model model,
								HttpSession session)
	{
		User validUser = userServ.loginUser(user.getEmail(), user.getPassword());
		if(validUser !=null)
		{
			if (validUser.isBanned()) {
	            model.addAttribute("errorMsg", "Your account has been banned. Contact admin.");
	            return "login";
	        }
			session.setAttribute("loggedInUser", validUser);
			model.addAttribute("modelName",validUser.getName());
			return "profile";
		}
		else
		{
			model.addAttribute("errorMsg","User is not registered");
			return "login";
		}
	}
    @GetMapping("/profile")
    public String viewProfile(Model model,HttpSession session)
    {
    	User user=(User) session.getAttribute("loggedInUser");
    	if (user == null) {
            return "redirect:/login";
        }

        if (user.isBanned()) {
            session.invalidate();
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "profile";
    }
    @GetMapping("/register")
    public String register(Model model)
    {
    	model.addAttribute("user",new User());
    	return "register";
    }
    @PostMapping("/regForm")
    public String registerUser(@ModelAttribute User user,
                               Model model, RedirectAttributes ra)
    {
        if (user.getName().isEmpty() ||
            user.getEmail().isEmpty() ||
            user.getPassword().isEmpty() ||
            user.getPhoneno().isEmpty() ||
            user.getCity().isEmpty()) {
            model.addAttribute("errorMsg", "All fields are required!");
            return "register";
        }
        else {
        	userServ.registerPage(user);
            ra.addFlashAttribute("successMsg", "Registration Success");
            return "redirect:/login";
        }  
    }
    @GetMapping("/logout")
	public String logout(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		
		if(session != null)
		{
			session.invalidate();
		}
		return "redirect:/login";
	}
}
