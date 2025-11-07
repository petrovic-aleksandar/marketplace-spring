package me.aco.marketplace.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.aco.marketplace.dto.ImageResponse;
import me.aco.marketplace.model.Image;
import me.aco.marketplace.model.Item;
import me.aco.marketplace.repository.ImagesRepository;

@Service
public class ImageService {

    @Autowired
    private ImagesRepository imagesRepository;

    public Image add(String path, Item item) {
        Image image = new Image();
        image.setPath(path);
        image.setItem(item);
        image.setFront(false);
        return imagesRepository.save(image);
    }

    public ImageResponse makeImageFront(Image image, Item item) {
        
        var currentFrontImages = imagesRepository.findFrontImageByItem(item);
        if (currentFrontImages != null) {
            currentFrontImages.setFront(false);
            imagesRepository.save(currentFrontImages);
        }
        image.setFront(true);
        var updatedImage = imagesRepository.save(image);
        return new ImageResponse(updatedImage);
        
    }

    public void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}
