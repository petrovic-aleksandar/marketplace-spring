package me.aco.marketplace.dto;

import lombok.Getter;
import lombok.Setter;
import me.aco.marketplace.model.Transfer;
import me.aco.marketplace.util.DateFormats;

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
		time = transfer.getCreatedAt() != null ? transfer.getCreatedAt().format(DateFormats.ISO_INSTANT_NO_MILLIS) : "";
		type = transfer.getType().toString();
		buyer = new UserResp(transfer.getBuyer());
		seller = new UserResp(transfer.getSeller());
		item = new ItemResp(transfer.getItem());
	}

}
