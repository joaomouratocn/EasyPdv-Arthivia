package br.com.arthivia.api.service;

import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.CategoryInsertDto;
import br.com.arthivia.api.model.entitys.CategoryEntity;
import br.com.arthivia.api.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
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
    void updateCategory() {

    }

    @Test
    void getCategoryById() {
    }
}