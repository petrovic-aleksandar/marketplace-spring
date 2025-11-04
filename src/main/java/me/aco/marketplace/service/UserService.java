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
		if (users.isEmpty())
			return null;
		return users.get(0);
	}
    
}
