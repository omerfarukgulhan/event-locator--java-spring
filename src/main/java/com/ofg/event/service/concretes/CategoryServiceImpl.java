package com.ofg.event.service.concretes;

import com.ofg.event.exception.general.NotFoundException;
import com.ofg.event.model.entity.Category;
import com.ofg.event.model.request.CategoryCreateRequest;
import com.ofg.event.model.request.CategoryUpdateRequest;
import com.ofg.event.model.response.CategoryResponse;
import com.ofg.event.repository.CategoryRepository;
import com.ofg.event.service.abstracts.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(CategoryResponse::new);
    }

    @Override
    public CategoryResponse getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryResponse::new)
                .orElseThrow(() -> new NotFoundException(categoryId));
    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .map(CategoryResponse::new)
                .orElseThrow(() -> new NotFoundException(name));
    }

    @Override
    public CategoryResponse addCategory(CategoryCreateRequest categoryCreateRequest) {
        Category category = new Category();
        category.setName(categoryCreateRequest.name());
        Category savedCategory = categoryRepository.save(category);
        return new CategoryResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(long categoryId, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(categoryId));
        category.setName(categoryUpdateRequest.name());
        Category updatedCategory = categoryRepository.save(category);
        return new CategoryResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(categoryId));
        categoryRepository.delete(category);
    }
}
