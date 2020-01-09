package com.zqj.blog.controller;

import com.zqj.blog.entity.Category;
import com.zqj.blog.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zqj.blog.util.Validations.check;

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
        check(category != null);
        check(StringUtils.isNotEmpty(category.getName()));

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

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        check(category != null, category.getId() != null, StringUtils.isNotEmpty(category.getName()));

        categoryService.updateCategory(category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
        check(id != null);

        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}
