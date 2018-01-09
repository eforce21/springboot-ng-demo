package com.eforce21.webscale.classic.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.eforce21.webscale.data", ignoreUnknownFields = false)
public class WebscaleDataConfig {

    private String basedir;

    private String datadir = "data";
    private String previewdir = "preview";
    private String thumbnaildir = "thumb";

    public String getBasedir() {
        return basedir;
    }

    public void setBasedir(String basedir) {
        this.basedir = basedir;
    }

    public String getDatadir() {
        return datadir;
    }

    public void setDatadir(String datadir) {
        this.datadir = datadir;
    }

    public String getPreviewdir() {
        return previewdir;
    }

    public void setPreviewdir(String previewdir) {
        this.previewdir = previewdir;
    }

    public String getThumbnaildir() {
        return thumbnaildir;
    }

    public void setThumbnaildir(String thumbnaildir) {
        this.thumbnaildir = thumbnaildir;
    }
}
