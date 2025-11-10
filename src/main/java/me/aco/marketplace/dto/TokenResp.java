package me.aco.marketplace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResp {
	
	private String accessToken;
	private String refreshToken;

	public TokenResp(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

}
