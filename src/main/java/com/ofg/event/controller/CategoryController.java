package com.ofg.event.controller;

import com.ofg.event.model.request.CategoryCreateRequest;
import com.ofg.event.model.request.CategoryUpdateRequest;
import com.ofg.event.model.response.CategoryResponse;
import com.ofg.event.service.abstracts.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(Pageable pageable) {
        Page<CategoryResponse> categories = categoryService.getAllCategories(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("categoryId") long categoryId) {
        CategoryResponse category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable("name") String name) {
        CategoryResponse category = categoryService.getCategoryByName(name);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest) {
        CategoryResponse category = categoryService.addCategory(categoryCreateRequest);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable("categoryId") long categoryId,
            @Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, categoryUpdateRequest);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
