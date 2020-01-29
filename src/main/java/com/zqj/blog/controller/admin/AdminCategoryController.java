package com.zqj.blog.controller.admin;

import com.zqj.blog.entity.Category;
import com.zqj.blog.entity.bo.CategoryFormBO;
import com.zqj.blog.service.CategoryService;
import com.zqj.blog.util.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> postCategory(@RequestBody @Validated CategoryFormBO categoryFormBO) {
        Category category = categoryFormBO.toPO();
        categoryService.createCategory(category);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody @Validated CategoryFormBO categoryFormBO) {
        Validations.expect(categoryFormBO != null && categoryFormBO.getId() != null);
        Category category = categoryFormBO.toPO();
        categoryService.updateCategory(category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
        Validations.expect(id != null);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") Integer id) {
        Validations.expect(id != null);
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
}
