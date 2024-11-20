package com.ofg.event.model.response;

import com.ofg.event.model.entity.Category;

public record CategoryResponse(
        Long id,
        String name
) {
    public CategoryResponse(Category category) {
        this(category.getId(), category.getName());
    }
}
