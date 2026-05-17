package in.spring.ecom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.spring.ecom.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>
{

	User findByEmail(String email);

}

