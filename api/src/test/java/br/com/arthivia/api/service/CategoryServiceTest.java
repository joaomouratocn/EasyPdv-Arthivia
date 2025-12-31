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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    CategoryService categoryService;

    @Test
    @DisplayName("Should save category successfully")
    void saveCategory() {
        // Arrange
        CategoryInsertDto categoryInsertDto = new CategoryInsertDto("TEST");
        SuccessResponse expectedResponse = new SuccessResponse("Category saved successfully");
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(null);
        // Act
        SuccessResponse result = categoryService.saveCategory(categoryInsertDto);
        // Assert
        assertEquals(expectedResponse.message(), result.message());
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    @DisplayName("Should update category name by id")
    void updateCategory_success() {
        //Arrange
        CategoryEntity category = new CategoryEntity("OLD");
        category.setCategoryId(1);
        when(categoryRepository.findById(1))
                .thenReturn(Optional.of(category));
        CategoryInsertDto dto = new CategoryInsertDto("NEW");

        //Act
        SuccessResponse response =
                categoryService.updateCategory(dto, 1);

        //Assert
        assertEquals("NEW", category.getName());
        assertEquals("Category saved successfully", response.message());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Should find category by id")
    void getCategoryByIdCase1() {
        //Arrange
        CategoryEntity category = new CategoryEntity("TEST");
        category.setCategoryId(1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        //Act
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(1);
        //Assert
        assertEquals("TEST", categoryResponseDto.name());
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("should return category not found")
    void getCategoryByIdCase2() {
        //Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());
        //Act && Assert
        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getCategoryById(1)
        );

        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should delete category by id")
    void deleteCategory() {
        //Arrange
        CategoryEntity category = new CategoryEntity("TEST");
        category.setCategoryId(1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        //Act
        SuccessResponse response = categoryService.deleteCategory(1);
        //Assert
        assertEquals("Category deleted successfully", response.message());
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    @DisplayName("should return category not found when deleting")
    void deleteCategory_CategoryNotFound() {
        //Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());
        //Act && Assert
        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.deleteCategory(1)
        );
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("should return category with product exception when updating")
    void updateCategory_CategoryNotFound() {
        //Arrange
        CategoryEntity categoryEntity = new CategoryEntity("TEST");
        categoryEntity.setCategoryId(1);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryEntity));
        when(productRepository.findAllByCategoryIdAndProductEnableTrue(1)).thenReturn(
                List.of(
                        new ProductEntity("1234567",
                                "PRODUCT",
                                categoryEntity.getCategoryId(),
                                "UNIT",
                                new BigDecimal("2.00"),
                                new BigDecimal("10.00"),
                                new BigDecimal("100"),
                                new BigDecimal("10"),
                                false
                        )
                )
        );

        //Act && Assert
        assertThrows(
                CategoryWithProductsException.class,
                () -> categoryService.deleteCategory(1)
        );
        verify(productRepository, times(1)).findAllByCategoryIdAndProductEnableTrue(1);
    }

    @Test
    void getAllCategories() {
        //Arrange
        when(categoryRepository.findAll()).thenReturn(
                List.of(
                        new CategoryEntity("CAT1"),
                        new CategoryEntity("CAT2")
                )
        );
        //Act
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        //Assert
        assertEquals(2, categories.size());
        assertEquals("CAT1", categories.get(0).name());
        verify(categoryRepository, times(1)).findAll();
    }
}