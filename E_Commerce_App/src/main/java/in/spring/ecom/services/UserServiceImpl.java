package in.spring.ecom.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.spring.ecom.entities.User;
import in.spring.ecom.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepo;
	@Override
	public User loginUser(String email, String password) {
		User validUser = userRepo.findByEmail(email);
		if(validUser !=null && validUser.getPassword().equals(password))
		{
			return validUser;
		}
		return null;
	}
	@Override
	public boolean registerPage(User user) {
		try
		{
			userRepo.save(user);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	@Override
	public void banUser(int id) {
		 User user = userRepo.findById(id).orElse(null);
	        if (user != null) {
	            user.setBanned(true);
	            userRepo.save(user);
	        }
	}
	@Override
	public void unbanUser(int id) {
		User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.setBanned(false);
            userRepo.save(user);
        }
	}
	@Override
	public Page<User> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return userRepo.findAll(pageable);
	}	
}
