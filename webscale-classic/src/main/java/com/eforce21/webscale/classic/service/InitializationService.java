package com.eforce21.webscale.classic.service;

import com.eforce21.webscale.classic.persistence.dao.ImageMetadataDao;
import com.eforce21.webscale.classic.persistence.entities.ImageMetadata;
import com.eforce21.webscale.classic.service.config.WebscaleDataConfig;
import com.eforce21.webscale.classic.util.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class InitializationService {

    private static final Logger LOG = LoggerFactory.getLogger(InitializationService.class);

    @Autowired
    private WebscaleDataConfig config;

    @Autowired
    private ImageMetadataDao imageMetadataDao;

    @PostConstruct
    public void generateSql() {
        if(imageMetadataDao.count() == 0) {
            LOG.info("Found empty database index. Initializing index from filestore");
            try {
                Files.walk(Paths.get(config.getBasedir(), config.getDatadir())).forEach(this::createImageMetadata);
            } catch (IOException e) {
                LOG.error("Could not initialize database index", e);
            }
            LOG.info("Finished generating database image index.");
        }

    }

    private void createImageMetadata(Path path) {
        File file = path.toFile();
        if(!file.isDirectory() && !file.getName().endsWith("md") && ! file.getName().startsWith(".")) {
            String name = file.getName();

            ImageMetadata image = new ImageMetadata();
            image.setName(name);
            image.setPath(config.getDatadir() + "/" + name);

            if(Files.exists(Paths.get(config.getBasedir(), config.getPreviewdir(), name))) {
                image.setDetailPath(config.getPreviewdir() + "/" + name);
            }
            if(Files.exists(Paths.get(config.getBasedir(), config.getThumbnaildir(), name))) {
                image.setThumbnailPath(config.getThumbnaildir() + "/" + name);
            }
            image.setMimeType("image/jpeg");
            image.setRecordingDate(ImageUtils.getRecordingDate(path));
            imageMetadataDao.save(image);
        }
    }
}
