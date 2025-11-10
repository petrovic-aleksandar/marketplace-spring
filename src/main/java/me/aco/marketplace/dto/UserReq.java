package me.aco.marketplace.dto;

import java.security.NoSuchAlgorithmException;

import lombok.Getter;
import lombok.Setter;
import me.aco.marketplace.enums.UserRole;
import me.aco.marketplace.model.User;
import me.aco.marketplace.util.SecurityUtil;

@Getter
@Setter
public class UserReq {
	
	private String username;
	private boolean updatePassword;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String role;
	
	public User toUser() throws NoSuchAlgorithmException {
		User user = new User();
		user.setUsername(username);
		user.setPassword(SecurityUtil.hashPassword(password));
		user.setName(name);
		user.setEmail(email);
		user.setPhone(phone);
		user.setRole(UserRole.valueOf(role));
		user.setActive(true);
		return user;
	}

}
