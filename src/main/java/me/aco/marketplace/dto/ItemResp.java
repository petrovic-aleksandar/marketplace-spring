package me.aco.marketplace.dto;

import lombok.Getter;
import lombok.Setter;
import me.aco.marketplace.model.Image;
import me.aco.marketplace.model.Item;
import me.aco.marketplace.model.ItemType;
import me.aco.marketplace.util.DateFormats;

@Getter
@Setter
public class ItemResp {
	
	private long id;
	private String name;
	private String description;
	private double price;
	private ItemType type;
	private boolean active;
	private boolean deleted;
	private String createdAt;
	private UserResp seller;
	private Image frontImage;
	
	public ItemResp(Item item) {
		super();
		this.id = item.getId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = item.getPrice();
		this.type = item.getType();
		this.active = item.isActive();
		this.createdAt = item.getCreatedAt() != null ? item.getCreatedAt().format(DateFormats.ISO_INSTANT_NO_MILLIS) : "";
		this.seller = new UserResp(item.getSeller());	
	}

}
