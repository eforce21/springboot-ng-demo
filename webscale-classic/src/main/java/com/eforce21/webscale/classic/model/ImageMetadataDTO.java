package com.eforce21.webscale.classic.model;

import java.io.Serializable;

public class ImageMetadataDTO implements Serializable {

    private static final long serialVersionUID = -2170786098448200220L;

    private long id;

    private String name;
    private String path;

    private String mimeType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
