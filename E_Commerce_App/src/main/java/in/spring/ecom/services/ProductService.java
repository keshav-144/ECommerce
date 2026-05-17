package in.spring.ecom.services;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import in.spring.ecom.entities.Product;

public interface ProductService 
{
	Page<Product> findPaginated(int pageNo,int pageSize);
	//method to add product
	Product addProduct(Product product);
	
	//method to get all product
	public List<Product> getAllProduct();
	
	//method to get product by id
	Product getProductById(Long id);
	
	//method to update product
	public Product updateProduct(Long id,Product product,MultipartFile file);
	
	//method to delete product
	public void deleteProduct(Long id);
}
