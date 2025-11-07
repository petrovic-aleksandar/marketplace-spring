package me.aco.marketplace.dto;

import lombok.Getter;
import lombok.Setter;
import me.aco.marketplace.model.Image;

@Getter
@Setter
public class ImageResponse {

    private long id;
	private String path;
	private boolean front;

    public ImageResponse(Image image) {
        this.id = image.getId();
        this.path = image.getPath();
        this.front = image.isFront();
    }
    
}
