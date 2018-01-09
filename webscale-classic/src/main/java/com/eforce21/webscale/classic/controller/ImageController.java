package com.eforce21.webscale.classic.controller;

import com.eforce21.webscale.classic.model.exception.NotFoundException;
import com.eforce21.webscale.classic.model.CategoryDTO;
import com.eforce21.webscale.classic.model.ImageDetailDTO;
import com.eforce21.webscale.classic.model.ImageMetadataDTO;
import com.eforce21.webscale.classic.model.exception.EmptyPathException;
import com.eforce21.webscale.classic.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public List<ImageMetadataDTO> loadAllImages(ImageGetParams params) {
        if(params.getPageNumber() != null && params.getAmount() != null) {
            if(params.getFilterString() != null) {
                return imageService.findAllImages(params.getPageNumber(), params.getAmount(), params.getFilterString());
            } else {
                return imageService.findAllImages(params.getPageNumber(), params.getAmount());
            }
        }

        if(params.getFilterString() != null) {
            return imageService.findAllImages(params.getFilterString());
        }

        return imageService.findAllImages();
    }

    @GetMapping("/{id}")
    public ImageDetailDTO loadImageDetails(@PathVariable("id") Long imageId) {
        return imageService.getImageDetails(imageId).orElseThrow(NotFoundException::new);
    }

    @PostMapping("/{id}/category")
    public ImageDetailDTO addCategoryToImage(@RequestBody CategoryDTO category, @PathVariable("id") Long imageId) {
        return imageService.addCategoryToImage(imageId, category.getId());
    }

    @GetMapping(value = "/{id}/data")
    public ResponseEntity<InputStreamResource> loadImageData(@PathVariable("id") Long imageId) {
        ImageDetailDTO imageDetails = imageService.getImageDetails(imageId).orElseThrow(NotFoundException::new);

        return getImageData(imageDetails, imageDetails.getPath());
    }

    @GetMapping(value = "/{id}/preview")
    public ResponseEntity<InputStreamResource> loadImagePreview(@PathVariable("id") Long imageId) {
        ImageDetailDTO imageDetails = imageService.getImageDetails(imageId).orElseThrow(NotFoundException::new);

        return getImageData(imageDetails, imageDetails.getPreviewPath());

    }

    @GetMapping(value = "/{id}/thumbnail")
    public ResponseEntity<InputStreamResource> loadImageThumbnail(@PathVariable("id") Long imageId) {
        ImageDetailDTO imageDetails = imageService.getImageDetails(imageId).orElseThrow(NotFoundException::new);

        return getImageData(imageDetails, imageDetails.getThumbnailPath());
    }

    @PostMapping
    public ImageMetadataDTO uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return imageService.saveImage(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private ResponseEntity<InputStreamResource> getImageData(ImageDetailDTO imageDetails, String path) {
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, imageDetails.getMimeType())
                    .header("Content-Disposition", "attachment; filename=" + imageDetails.getName())
                    .body(new InputStreamResource(imageService.readImage(path)));
        } catch (EmptyPathException e) {
            throw new NotFoundException();
        }
    }

}
