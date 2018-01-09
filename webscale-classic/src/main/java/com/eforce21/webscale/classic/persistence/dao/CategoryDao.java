package com.eforce21.webscale.classic.persistence.dao;

import com.eforce21.webscale.classic.persistence.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
