package in.spring.ecom.controller;

import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import in.spring.ecom.entities.Product;
import in.spring.ecom.services.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService proServ;

    // Show Product Management Page
    @GetMapping("/product/manage")
    public String productPage(Model model, 
    		@RequestParam(defaultValue = "1") int pageNo)
    {
    	int pageSize = 2;
		Page<Product> page = proServ.findPaginated(pageNo, pageSize);

        model.addAttribute("products", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "product";
    }

    // Open Add Product Page
    @GetMapping("/addProduct")
    public String addProductPage(Model model)
    {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    // Save Product
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile file) {

        product.setUpdatedOn(LocalDate.now());
        product.setImageName(file.getOriginalFilename());

        proServ.addProduct(product);

        return "redirect:/product/manage";
    }

    // Edit Product
    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable Long id, Model model)
    {
        model.addAttribute("product", proServ.getProductById(id));
        return "editProduct";
    }

    // Update Product
    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product,
    		@RequestParam("imageFile") MultipartFile file)
    {
        proServ.updateProduct(product.getId(), product,file);
        return "redirect:/product/manage";
    }

    // Delete Product
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id)
    {
        proServ.deleteProduct(id);
        return "redirect:/product/manage";
    }
}
