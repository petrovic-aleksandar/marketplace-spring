package me.aco.marketplace.dto;

import lombok.Getter;
import lombok.Setter;
import me.aco.marketplace.model.User;

@Getter
@Setter
public class UserResp {
	
	private long id;
	private String username;
	private String name;
	private String email;
	private String phone;
	private double balance;
	private String role;
	private boolean active;
	
	public UserResp(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.name = user.getName();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.balance = user.getBalance();
		this.role = user.getRole().toString();
		this.active = user.isActive();
	}

}
