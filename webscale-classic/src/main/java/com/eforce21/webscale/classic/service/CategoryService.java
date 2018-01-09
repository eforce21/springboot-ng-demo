package com.eforce21.webscale.classic.service;

import com.eforce21.libraries.security.annotations.AuthenticatedService;
import com.eforce21.webscale.classic.model.CategoryDTO;
import com.eforce21.webscale.classic.persistence.dao.CategoryDao;
import com.eforce21.webscale.classic.persistence.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AuthenticatedService
@Transactional
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<CategoryDTO> findAllCategories() {
        return categoryDao.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

     public CategoryDTO createCategory(String name) {
         Category category = categoryDao.findByName(name).orElseGet(() -> createNewCategory(name));
         return map(category);
     }

     private Category createNewCategory(String name) {
         Category category = new Category();
         category.setName(name);
         categoryDao.save(category);
         return category;
     }

     private CategoryDTO map(Category category) {
         CategoryDTO mapped = new CategoryDTO();
         mapped.setName(category.getName());
         mapped.setId(category.getId());
         mapped.setImages(category.getImages().size());
         return mapped;
     }


}
