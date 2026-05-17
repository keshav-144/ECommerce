package in.spring.ecom.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import in.spring.ecom.entities.Admin;
import in.spring.ecom.repositories.AdminRepository;
@Service
public class AdminServiceImpl implements AdminService
{
	@Autowired
	private AdminRepository adminRepo;
	
	@Override
	public Admin login(String email, String password) {
		return adminRepo.findByEmailAndPassword(email, password);
	}

}
