package com.eforce21.webscale.classic.controller;

import com.eforce21.webscale.classic.model.CategoryDTO;
import com.eforce21.webscale.classic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryDTO createNewCategory(@RequestBody CategoryDTO category) {
        return categoryService.createCategory(category.getName());
    }

    @GetMapping
    public List<CategoryDTO> findAllCategories() {
        return categoryService.findAllCategories();
    }

}
