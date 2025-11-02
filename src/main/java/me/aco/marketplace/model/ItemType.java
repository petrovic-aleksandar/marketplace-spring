package me.aco.marketplace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@NamedQueries({ @NamedQuery(name = ItemType.getAll, query = "select it from ItemType it"),
	@NamedQuery(name = ItemType.getById, query = "select it from ItemType it where it.id = :id")
})
@Getter
@Setter
public class ItemType {

	public static final String getAll = "GetAllItemTypes";
	public static final String getById = "GetItemTypeById";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_type_seq_gen")
	@SequenceGenerator(name = "item_type_seq_gen", sequenceName = "item_type_seq")
	private long id;
	private String name;
	private String description;
	private String imagePath;
}
