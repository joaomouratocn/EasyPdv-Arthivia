package br.com.arthivia.api.service;

import br.com.arthivia.api.infra.exceptions.custom.CategoryNotFoundException;
import br.com.arthivia.api.infra.exceptions.custom.CategoryWithProductsException;
import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.CategoryInsertDto;
import br.com.arthivia.api.model.dtos.CategoryResponseDto;
import br.com.arthivia.api.model.entitys.CategoryEntity;
import br.com.arthivia.api.model.entitys.ProductEntity;
import br.com.arthivia.api.repository.CategoryRepository;
import br.com.arthivia.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    public SuccessResponse saveCategory(CategoryInsertDto categoryInsertDto) {
        CategoryEntity categoryEntity = new CategoryEntity(categoryInsertDto);

        categoryRepository.save(categoryEntity);

        return new SuccessResponse("Category saved successfully");
    }

    public SuccessResponse updateCategory(CategoryInsertDto categoryInsertDto, Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        categoryEntity.setName(categoryInsertDto.categoryName());

        categoryRepository.save(categoryEntity);

        return new SuccessResponse("Category updated successfully");
    }

    public SuccessResponse deleteCategory(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        List<ProductEntity> allByCategoryCategoryIdAndProductEnableTrue = productRepository.findAllByCategoryIdAndProductEnableTrue(categoryId);
        if (!allByCategoryCategoryIdAndProductEnableTrue.isEmpty()) {
            throw new CategoryWithProductsException();
        }

        categoryRepository.delete(categoryEntity);

        return new SuccessResponse("Category deleted successfully");
    }

    public CategoryResponseDto getCategoryById(Integer categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        return new CategoryResponseDto(category);
    }

    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryResponseDto::new).toList();
    }
}
