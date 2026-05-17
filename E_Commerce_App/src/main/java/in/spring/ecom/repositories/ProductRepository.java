package in.spring.ecom.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import in.spring.ecom.entities.Product;


public interface ProductRepository extends JpaRepository<Product,Long>
{

}

