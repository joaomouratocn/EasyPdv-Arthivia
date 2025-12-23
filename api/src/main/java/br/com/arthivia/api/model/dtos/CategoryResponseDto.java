package br.com.arthivia.api.model.dtos;

import br.com.arthivia.api.model.entitys.CategoryEntity;

public record CategoryResponseDto(
        Integer id,
        String name
) {
    public CategoryResponseDto(CategoryEntity category) {
        this(category.getCategoryId(), category.getName());
    }
}
