package me.aco.marketplace.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import me.aco.marketplace.enums.TransferType;

@Entity
@NamedQueries({
		@NamedQuery(name = Transfer.getByUser, query = "select t from Transfer t where t.seller = :user or t.buyer = :user") })
@Getter
@Setter
public class Transfer {

	public static final String getByUser = "getTransfersByUser";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_seq_gen")
	@SequenceGenerator(name = "transfer_seq_gen", sequenceName = "transfer_seq")
	private long id;
	private double amount;
	private LocalDateTime createdAt;
	private TransferType type;
	@ManyToOne
	private User buyer;
	@ManyToOne
	private User seller;
	@ManyToOne
	private Item item;
    
}
