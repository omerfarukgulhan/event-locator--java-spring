package com.ofg.event.service.abstracts;

import com.ofg.event.model.entity.Category;
import com.ofg.event.model.request.CategoryCreateRequest;
import com.ofg.event.model.request.CategoryUpdateRequest;
import com.ofg.event.model.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryResponse> getAllCategories(Pageable pageable);

    CategoryResponse getCategoryResponseById(long categoryId);

    Category getCategoryEntityById(long categoryId);

    CategoryResponse getCategoryByName(String name);

    CategoryResponse addCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse updateCategory(long categoryId, CategoryUpdateRequest categoryUpdateRequest);

    void deleteCategory(long CategoryId);
}
