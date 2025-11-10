package me.aco.marketplace.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.aco.marketplace.dto.UserReq;
import me.aco.marketplace.enums.UserRole;
import me.aco.marketplace.model.User;
import me.aco.marketplace.repository.UsersRepository;
import me.aco.marketplace.util.SecurityUtil;

@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;

	public User update(UserReq request, User user) throws NoSuchAlgorithmException {
		user.setUsername(request.getUsername());
		if (request.isUpdatePassword()) {
			user.setPassword(SecurityUtil.hashPassword(request.getPassword()));
		}
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		user.setRole(UserRole.valueOf(request.getRole()));
		return usersRepository.save(user);
	}
    
}
