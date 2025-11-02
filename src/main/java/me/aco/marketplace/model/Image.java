package me.aco.marketplace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity
@NamedQueries({@NamedQuery(name = Image.getBySellerId, query = "select im from Image im left join Item it on im.item = it where it.seller.id = :sellerId"),
		@NamedQuery(name = Image.getByTypeId, query = "select im from Image im left join Item it on im.item = it where it.type.id = :typeId"),
		@NamedQuery(name = Image.getFrontForItem, query = "select im from Image im where im.item = :item and im.front = true"),
		@NamedQuery(name = Image.getByItem, query = "select im from Image im where im.item = :item order by im.id asc"),
		@NamedQuery(name = Image.getById, query = "select im from Image im where id = :id")})
@Getter
@Setter
public class Image {
	
	public static final String getBySellerId = "getImagesBySellerId";
	public static final String getByTypeId = "getImagesByTypeId";
	public static final String getByItem = "getImagesByItem";
	public static final String getFrontForItem = "getImageFrontForItem";
	public static final String getById = "getImageById";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq_gen")
	@SequenceGenerator(name = "image_seq_gen", sequenceName = "image_seq")
	private long id;
	private String path;
	@JsonIgnore
	@ManyToOne
	private Item item;
	private boolean front;
    
}
