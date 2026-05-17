package in.spring.ecom.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import in.spring.ecom.entities.Admin;



public interface AdminRepository extends JpaRepository<Admin,Long>
{
	Admin findByEmailAndPassword(String email,String password);
	
	
}

