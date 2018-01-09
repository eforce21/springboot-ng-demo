package com.eforce21.webscale.classic.persistence.dao;

import com.eforce21.webscale.classic.persistence.entities.ImageMetadata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageMetadataDao extends JpaRepository<ImageMetadata, Long> {

    List<ImageMetadata> findByName(String name);

    @Query(name = "findByCategoryName", value = "select i from ImageMetadata i join i.categories c where c.name like :categoryName")
    List<ImageMetadata> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageRequest);

    List<ImageMetadata> findByThumbnailPathIsNullOrDetailPathIsNull();

}
