package me.aco.marketplace.dto;

import lombok.Getter;
import lombok.Setter;
import me.aco.marketplace.enums.TransferType;

@Getter
@Setter
public class TransferReq {
	
	private double amount;
	private TransferType type;
	private long buyerId;
	private long sellerId;
	private long itemId;

}
