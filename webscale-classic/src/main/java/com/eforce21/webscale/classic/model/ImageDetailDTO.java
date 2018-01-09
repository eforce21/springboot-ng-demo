package com.eforce21.webscale.classic.model;

import java.util.Date;
import java.util.Set;

public class ImageDetailDTO extends ImageMetadataDTO {

    private static final long serialVersionUID = -5429523719075653522L;

    private long size;
    private long width;
    private long height;

    private long thumbnailWidth;
    private long thumbnailHeight;

    private Date recordingDate;

    private Set<String> categories;

    private String previewPath;
    private String thumbnailPath;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(long thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public long getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(long thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public Date getRecordingDate() {
        return recordingDate;
    }

    public void setRecordingDate(Date recordingDate) {
        this.recordingDate = recordingDate;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public String getPreviewPath() {
        return previewPath;
    }

    public void setPreviewPath(String previewPath) {
        this.previewPath = previewPath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
