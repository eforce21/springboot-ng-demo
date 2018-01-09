package com.eforce21.webscale.classic.service;

import com.eforce21.webscale.classic.persistence.dao.ImageMetadataDao;
import com.eforce21.webscale.classic.persistence.entities.ImageMetadata;
import com.eforce21.webscale.classic.service.config.WebscaleDataConfig;
import com.eforce21.webscale.classic.util.ImageUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class ThumbnailService {

    private static final Logger LOG = LoggerFactory.getLogger(ThumbnailService.class);

    @Autowired
    private ImageMetadataDao imageMetadataDao;
    @Autowired
    private WebscaleDataConfig config;

    @Scheduled(fixedRate = 600000)
    public void scaleImages() {
        LOG.info("Starting scan for images without thumbnails");
        List<ImageMetadata> withoutThumbnail = imageMetadataDao.findByThumbnailPathIsNullOrDetailPathIsNull();
        LOG.info("Found {} images", withoutThumbnail.size());
        if(!withoutThumbnail.isEmpty()) {
            CompletableFuture[] completableFutures = withoutThumbnail.stream().map(this::scaleImage).toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(completableFutures).join();
            LOG.info("Finished thumbnail run");
        }
    }

    @Async
    public CompletableFuture<Boolean> scaleImage(ImageMetadata imageMetadata) {
        String fileName = ImageUtils.calculateFileName(imageMetadata.getName(), imageMetadataDao, 1);

        try {
            if (imageMetadata.getDetailPath() == null) {
                String path = scaleAndWriteImage(imageMetadata, fileName, config.getPreviewdir(), 1280);
                imageMetadata.setDetailPath(path);
            }
            if (imageMetadata.getThumbnailPath() == null) {
                String path = scaleAndWriteImage(imageMetadata, fileName, config.getThumbnaildir(), 400);
                imageMetadata.setThumbnailPath(path);
            }
            imageMetadataDao.saveAndFlush(imageMetadata);
        } catch (IOException e) {
            LOG.error("Could not scale image {}.", imageMetadata.getPath(), e);
            return CompletableFuture.completedFuture(false);
        }
        return CompletableFuture.completedFuture(true);
    }

    private String scaleAndWriteImage(ImageMetadata imageMetadata, String fileName, String dir, int size) throws IOException {
        try(InputStream inputStream = Files.newInputStream(Paths.get(config.getBasedir(), imageMetadata.getPath()));
            OutputStream outputStream = Files.newOutputStream(Paths.get(config.getBasedir(), dir, fileName), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {

            BufferedImage image = ImageIO.read(inputStream);
            BufferedImage resized = Scalr.resize(image, size);

            ImageIO.write(resized, "jpg", outputStream);

            return dir + "/" + fileName;
        }
    }
}
