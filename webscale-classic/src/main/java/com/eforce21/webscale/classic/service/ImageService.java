package com.eforce21.webscale.classic.service;

import com.eforce21.libraries.security.annotations.AuthenticatedService;
import com.eforce21.webscale.classic.model.exception.NotFoundException;
import com.eforce21.webscale.classic.model.ImageDetailDTO;
import com.eforce21.webscale.classic.model.ImageMetadataDTO;
import com.eforce21.webscale.classic.model.exception.EmptyPathException;
import com.eforce21.webscale.classic.persistence.dao.CategoryDao;
import com.eforce21.webscale.classic.persistence.dao.ImageMetadataDao;
import com.eforce21.webscale.classic.persistence.entities.Category;
import com.eforce21.webscale.classic.persistence.entities.ImageMetadata;
import com.eforce21.webscale.classic.service.config.WebscaleDataConfig;
import com.eforce21.webscale.classic.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AuthenticatedService
@Transactional
public class ImageService {

    private static final String RECORDING_DATE = "recordingDate";

    @Autowired
    private WebscaleDataConfig config;

    @Autowired
    private ImageMetadataDao imageMetadataDao;
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ThumbnailService thumbnailService;

    public List<ImageMetadataDTO> findAllImages() {
        return findAllImages(0, Integer.MAX_VALUE);
    }

    public List<ImageMetadataDTO> findAllImages(int pageNumber, int amount) {
        Sort sort = new Sort(Sort.Direction.DESC, RECORDING_DATE);
        PageRequest pageRequest = new PageRequest(pageNumber, amount, sort);

        return imageMetadataDao.findAll(pageRequest).getContent()
                .stream().map(this::mapMetadata).collect(Collectors.toList());
    }

    public List<ImageMetadataDTO> findAllImages(String filter) {
        return findAllImages(0, Integer.MAX_VALUE, filter);
    }

    public List<ImageMetadataDTO> findAllImages(int pageNumber, int amount, String filter) {
        Sort sort = new Sort(Sort.Direction.DESC, RECORDING_DATE);
        PageRequest pageRequest = new PageRequest(pageNumber, amount, sort);
        return imageMetadataDao.findByCategoryName(filter + "%", pageRequest)
                .stream().map(this::mapMetadata).collect(Collectors.toList());
    }

    public Optional<ImageDetailDTO> getImageDetails(Long id) {
        Optional<ImageMetadata> one = imageMetadataDao.findById(id);
        if(one.isPresent()) {
            ImageMetadata imageMetadata = one.get();
            ImageDetailDTO image = new ImageDetailDTO();
            image.setId(imageMetadata.getId());
            image.setName(imageMetadata.getName());
            image.setPath(imageMetadata.getPath());
            image.setPreviewPath(imageMetadata.getDetailPath());
            image.setThumbnailPath(imageMetadata.getThumbnailPath());
            image.setMimeType(imageMetadata.getMimeType());
            image.setRecordingDate(imageMetadata.getRecordingDate());
            image.setCategories(imageMetadata.getCategories().stream().map(Category::getName).collect(Collectors.toSet()));

            return Optional.of(image);
        }
        return Optional.empty();

    }

    private ImageMetadataDTO mapMetadata(ImageMetadata image) {
        ImageMetadataDTO mapped = new ImageMetadataDTO();
        mapped.setMimeType(image.getMimeType());
        mapped.setName(image.getName());
        mapped.setPath(image.getPath());
        mapped.setId(image.getId());

        return mapped;
    }

    public ImageMetadataDTO saveImage(InputStream uploadStream, String fileName, String mimeType) {
        String name = ImageUtils.calculateFileName(fileName, imageMetadataDao, 0);
        Path targetPath = Paths.get(config.getBasedir(), config.getDatadir(), name);

        try {
            Files.copy(uploadStream, targetPath);
            ImageMetadata imageMetadata = new ImageMetadata();
            imageMetadata.setMimeType(mimeType);
            imageMetadata.setPath(config.getDatadir() + "/" + name);
            imageMetadata.setName(fileName);
            imageMetadata.setRecordingDate(ImageUtils.getRecordingDate(targetPath));

            imageMetadataDao.save(imageMetadata);


            thumbnailService.scaleImage(imageMetadata);

            return mapMetadata(imageMetadata);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream readImage(String path) {

        if(path == null) {
            throw new EmptyPathException();
        }
        try {
            return Files.newInputStream(Paths.get(config.getBasedir(), path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageDetailDTO addCategoryToImage(Long imageId, Long categoryId) {
        Optional<ImageMetadata> image = imageMetadataDao.findById(imageId);
        Optional<Category> category = categoryDao.findById(categoryId);
        if(!image.isPresent() || !category.isPresent()) {
            throw new NotFoundException();
        }

        image.get().getCategories().add(category.get());

        return getImageDetails(imageId).orElse(null);
    }
}
