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

    // Open Dashboard Page
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session)
    {
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");

        if(admin == null)
        {
            return "redirect:/admin/login";
        }

        return "Dashboard";
    }

    // Open Login Page
    @GetMapping("/admin/login")
    public String showLoginPage()
    {
        return "admin";
    }

    // Handle Login
    @PostMapping("/admin/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session)
    {
        Admin admin = aService.login(email, password);

        if(admin != null)
        {
            session.setAttribute("loggedInAdmin", admin);

            return "redirect:/dashboard";
        }
        else
        {
            model.addAttribute("error", "Invalid Credentials");

            return "admin";
        }
    }

    // Logout
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
