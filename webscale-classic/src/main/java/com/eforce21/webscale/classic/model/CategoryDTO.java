package com.eforce21.webscale.classic.model;

import java.io.Serializable;

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = -8561795228031843257L;

    private Long id;

    private String name;
    private int images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
