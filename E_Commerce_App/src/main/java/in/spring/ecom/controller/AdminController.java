package in.spring.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.spring.ecom.entities.Admin;
import in.spring.ecom.services.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController 
{
	@Autowired
	private AdminService aService;
	 @GetMapping("/Dashboard")
	    public String dashboard() {
	        return "Dashboard";
	    }
	
	// Open adminLogin page
    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "admin";
    }
    
   @PostMapping("/admin/login")
public String login(@RequestParam String email, 
                    @RequestParam String password, 
                    Model model,
                    HttpSession session)  // add this
{
    Admin admin = aService.login(email, password);
    if(admin != null)
    {
        session.setAttribute("loggedInAdmin", admin);  // add this
        return "redirect:/Dashboard";  // use redirect
    }
    else
    {
        model.addAttribute("error", "Admin not found");
        return "admin";  // lowercase to match your template
    }
}
    @GetMapping("/admin/logout")
	public String logout(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		
		if(session != null)
		{
			session.invalidate();
		}
		return "redirect:/admin/login";
	}
}
