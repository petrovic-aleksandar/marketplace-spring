package me.aco.marketplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.aco.marketplace.model.User;
import me.aco.marketplace.repository.UsersRepository;

@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;

   public User getByUsername(String username) {
		List<User> users = usersRepository.findByUsername(username);
		if (users.size() == 0)
			return null;
		else
			return users.get(0);
	}
	
	public User saveUser(User user) {
		user = usersRepository.save(user);
		return user;
	} 
    
}
