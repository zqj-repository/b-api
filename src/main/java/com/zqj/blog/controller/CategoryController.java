package com.zqj.blog.controller;

import com.zqj.blog.entity.Category;
import com.zqj.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable("id") Integer id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("/all")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

}
