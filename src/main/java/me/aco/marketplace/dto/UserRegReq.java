package me.aco.marketplace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegReq {

	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;

}
