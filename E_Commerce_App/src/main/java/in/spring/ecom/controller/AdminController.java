package in.spring.ecom.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.sp.main.entities.Admin;
import in.sp.main.services.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController 
{
	@Autowired
	private AdminService aService;
	
	 @GetMapping("/dashboard")
	    public String dashboard() {
	        return "Dashboard";
	    }
	
	// Open adminLogin page
    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "admin";
    }
    
    @PostMapping("/admin/login")
    public String login(@RequestParam String email,String password,Model model)
    {
    	Admin admin = aService.login(email, password);
    	if(admin !=null)
    	{
    		return "Dashboard";
    	}
    	else
    	{
    		model.addAttribute("error","Admin not found");
    		return "admin";
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

