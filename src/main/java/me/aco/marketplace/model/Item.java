package me.aco.marketplace.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@NamedQueries({ @NamedQuery(name = Item.getBySellerId, query = "select i from Item i where i.deleted = false and i.seller.id = :userId order by i.id desc"),
		@NamedQuery(name = Item.getById, query = "select i from Item i where i.deleted = false and i.id = :id"),
		@NamedQuery(name = Item.getByType, query = "select i from Item i where i.deleted = false and i.type = :type and i.active = true order by i.id desc")})
@Getter
@Setter
public class Item {

	public static final String getBySellerId = "getItemsBySellerId";
	public static final String getById = "getItemById";
	public static final String getByType = "getItemsByType";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_gen")
	@SequenceGenerator(name = "item_seq_gen", sequenceName = "item_seq")
	private long id;
	private String name;
	private String description;
	private double price;
	@ManyToOne
	private ItemType type;
	private boolean active;
	private boolean deleted;
	@JsonFormat(pattern =  "dd-MM-yyyy")
	private LocalDateTime createdAt;
	@ManyToOne
	private User seller;

	@OneToMany(mappedBy = "item")
	private List<Image> images;

}
