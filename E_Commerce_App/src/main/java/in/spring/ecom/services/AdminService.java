package in.spring.ecom.services;

import in.spring.ecom.entities.Admin;

public interface AdminService 
{
	Admin login(String email,String password);
}
