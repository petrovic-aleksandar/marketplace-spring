package me.aco.marketplace.dto;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import me.aco.marketplace.model.Transfer;

@Getter
@Setter
public class TransferResp {
	
	private long id;
	private double amount;
	private String time;
	private String type;
	private UserResp buyer;
	private UserResp seller;
	private ItemResp item;
	
	public TransferResp(Transfer transfer) {
		id = transfer.getId();
		amount = transfer.getAmount();
		time = transfer.getCreatedAt() != null ? transfer.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")) : "";
		type = transfer.getType().toString();
		buyer = new UserResp(transfer.getBuyer());
		seller = new UserResp(transfer.getSeller());
		item = new ItemResp(transfer.getItem());
	}

}
