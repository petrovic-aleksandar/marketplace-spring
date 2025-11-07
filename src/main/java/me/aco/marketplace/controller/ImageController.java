package me.aco.marketplace.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import me.aco.marketplace.dto.ImageResponse;
import me.aco.marketplace.model.Image;
import me.aco.marketplace.repository.ImagesRepository;
import me.aco.marketplace.repository.ItemsRepository;
import me.aco.marketplace.service.ImageService;

@Async("asyncExecutor")
@RequestMapping("/Image")
@RestController
public class ImageController {

    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private ImageService imageService;

    @GetMapping("/getByItemId/{itemId}")
    public CompletableFuture<ResponseEntity<List<ImageResponse>>> getImagesByItemId(@PathVariable("itemId") Long itemId) {
        return CompletableFuture.supplyAsync(() -> {
            var images = imagesRepository.findByItemId(itemId);
            var resp = images.stream().map(ImageResponse::new).toList();
            return ResponseEntity.ok(resp);
        });
    }

    @PostMapping(value = "/{itemId}", consumes = {"multipart/form-data"})
	public CompletableFuture<ResponseEntity<ImageResponse>> add(@PathVariable("itemId") Long itemId, @RequestParam("file") MultipartFile file) {
		var itemOpt = itemsRepository.findById(itemId);
        if (itemOpt.isEmpty())  
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var item = itemOpt.get();
		
		String imagesDir = System.getProperty("marketplace.imagesdirectory");
		String imageDir = imagesDir + item.getSeller().getId() + File.separatorChar + item.getId() + File.separatorChar;
		String uploadedFileLocation = imageDir + file.getName();
		try {
			Files.createDirectories(java.nio.file.Path.of(imageDir));
			File objFile = new File(uploadedFileLocation);
			if (objFile.exists())
				objFile.delete();
			imageService.saveToFile(file.getInputStream(), uploadedFileLocation);
			Image image = imageService.add(file.getName(), item);
			return CompletableFuture.completedFuture(ResponseEntity.ok(new ImageResponse(image)));
		} catch (IOException e) {
			e.printStackTrace();
			return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
		}
	}

    @PostMapping("/front/{imageId}")
    public CompletableFuture<ResponseEntity<ImageResponse>> makeImageFront(@PathVariable("imageId") Long imageId) {
        return CompletableFuture.supplyAsync(() -> {
            var imageOpt = imagesRepository.findById(imageId);
            if (imageOpt.isEmpty())
                return ResponseEntity.notFound().build();
            var image = imageOpt.get();
            var updatedImage = imageService.makeImageFront(image, image.getItem());
            if (updatedImage == null)
                return ResponseEntity.internalServerError().build();
            return ResponseEntity.ok(updatedImage);
        });
    }

    @DeleteMapping("/{imageId}")
    public CompletableFuture<ResponseEntity<Void>> deleteImageById(@PathVariable("imageId") Long imageId) {
        return CompletableFuture.supplyAsync(() -> {
            if (!imagesRepository.existsById(imageId))
                return ResponseEntity.notFound().build();
            imagesRepository.deleteById(imageId);
            return ResponseEntity.ok().build();
        });
    }
    
}
