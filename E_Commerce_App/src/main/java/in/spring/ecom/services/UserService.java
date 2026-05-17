package in.spring.ecom.services;

import java.util.List;


import org.springframework.data.domain.Page;

import in.spring.ecom.entities.User;

public interface UserService {
	public User loginUser(String email,String password);
	public boolean registerPage(User user);
	List<User> getAllUsers();
	Page<User> findPaginated(int pageNo,int pageSize);
    void banUser(int id);
    void unbanUser(int id);
}
