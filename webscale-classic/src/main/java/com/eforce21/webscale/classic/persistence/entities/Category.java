package com.eforce21.webscale.classic.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "webscale_category", uniqueConstraints = {
        @UniqueConstraint(name = "unique_category_name", columnNames = {"name"})
})
public class Category implements Serializable {

    private static final long serialVersionUID = -4943686856547004048L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<ImageMetadata> images = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ImageMetadata> getImages() {
        return images;
    }

    public void setImages(Set<ImageMetadata> images) {
        this.images = images;
    }
}
