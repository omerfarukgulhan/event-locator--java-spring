package com.ofg.event.controller;

import com.ofg.event.core.util.response.ResponseUtil;
import com.ofg.event.core.util.results.ApiDataResponse;
import com.ofg.event.core.util.results.ApiResponse;
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

    private static final String CATEGORIES_FETCH_SUCCESS = "app.msg.categories.fetch.success";
    private static final String CATEGORY_FETCH_SUCCESS = "app.msg.category.fetch.success";
    private static final String CATEGORY_CREATE_SUCCESS = "app.msg.category.create.success";
    private static final String CATEGORY_UPDATE_SUCCESS = "app.msg.category.update.success";
    private static final String CATEGORY_DELETE_SUCCESS = "app.msg.category.delete.success";

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<CategoryResponse>>> getAllCategories(Pageable pageable) {
        Page<CategoryResponse> categories = categoryService.getAllCategories(pageable);
        return ResponseUtil.createApiDataResponse(categories, CATEGORIES_FETCH_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiDataResponse<CategoryResponse>> getCategoryById(@PathVariable("categoryId") long categoryId) {
        CategoryResponse category = categoryService.getCategoryResponseById(categoryId);
        return ResponseUtil.createApiDataResponse(category, CATEGORY_FETCH_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiDataResponse<CategoryResponse>> getCategoryByName(@PathVariable("name") String name) {
        CategoryResponse category = categoryService.getCategoryByName(name);
        return ResponseUtil.createApiDataResponse(category, CATEGORY_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<CategoryResponse>> addCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest) {
        CategoryResponse category = categoryService.addCategory(categoryCreateRequest);
        return ResponseUtil.createApiDataResponse(category, CATEGORY_CREATE_SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiDataResponse<CategoryResponse>> updateCategory(
            @PathVariable("categoryId") long categoryId,
            @Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, categoryUpdateRequest);
        return ResponseUtil.createApiDataResponse(updatedCategory, CATEGORY_UPDATE_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseUtil.createApiResponse(CATEGORY_DELETE_SUCCESS, HttpStatus.NO_CONTENT);
    }
}
