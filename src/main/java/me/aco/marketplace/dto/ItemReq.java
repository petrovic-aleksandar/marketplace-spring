package me.aco.marketplace.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemReq {
	
	private String name;
	private String description;
	private double price;
	private long typeId;
	private boolean active;
	private LocalDateTime createdAt;
	private long sellerId;
	
}
