package in.spring.ecom.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.spring.ecom.entities.Product;
import in.spring.ecom.repositories.CartRepository;
import in.spring.ecom.repositories.ProductRepository;
import jakarta.transaction.Transactional;
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository proRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Override
	public Product addProduct(Product product) {
		return proRepo.save(product);
	}

	@Override
	public List<Product> getAllProduct() {
		return proRepo.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		return proRepo.findById(id).orElse(null);
	}

	@Override
	public Product updateProduct(Long id, Product product,MultipartFile file) {
		Product existing = proRepo.findById(id).orElse(null);
		existing.setName(product.getName());
		existing.setDescription(product.getDescription());
		existing.setOriginalPrice(product.getOriginalPrice());
		existing.setDiscountedPrice(product.getDiscountedPrice());
		existing.setUpdatedOn(product.getUpdatedOn());
		if(!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
	        existing.setImageName(fileName);
        }

        return proRepo.save(existing);
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {

	    Product product = proRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Product not found"));
	    cartRepo.deleteByProductId(id); 
	    proRepo.delete(product);
	}

	@Override
	public Page<Product> findPaginated(int pageNo, int pageSize) {
		 Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
	     return proRepo.findAll(pageable);
	}
}
