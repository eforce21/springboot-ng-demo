package com.eforce21.webscale.classic.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.eforce21.webscale.classic.persistence.dao.ImageMetadataDao;
import com.eforce21.webscale.classic.persistence.entities.ImageMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public final class ImageUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

    public static Date getRecordingDate(Path path) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if(directory != null) {
                return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            }
        } catch (ImageProcessingException | IOException e) {
            LOG.warn("Cannot read EXIF of Image {}", path, e);
        }
        return new Date(path.toFile().lastModified());
    }

    public static String calculateFileName(String fileName, ImageMetadataDao imageMetadataDao, int limit) {
        List<ImageMetadata> existingForName = imageMetadataDao.findByName(fileName);
        String name = fileName;
        if(existingForName.size() > limit) {
            return existingForName.size() + "_" + name;
        }
        return name;
    }

}
