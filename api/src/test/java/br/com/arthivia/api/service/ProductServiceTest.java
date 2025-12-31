package br.com.arthivia.api.service;

import br.com.arthivia.api.infra.exceptions.custom.CategoryNotFoundException;
import br.com.arthivia.api.infra.exceptions.custom.ProductAlreadyRegisteredException;
import br.com.arthivia.api.infra.exceptions.custom.ProductNotFoundException;
import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.ProductInsertDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    String BAR_CODE = "1234567";
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("should save product successfully")
    void saveProductCase1() {
        //Arrange
        ProductInsertDto productInsertDto = createProductInsertDTO(BAR_CODE);
        CategoryEntity categoryEntity = new CategoryEntity("TEST");

        when(productRepository.findByBarCode(BAR_CODE)).thenReturn(Optional.empty());
        when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryEntity));

        //Act
        SuccessResponse successResponse = productService.saveProduct(productInsertDto);

        //Assert
        assertEquals("Product Saved successfully", successResponse.message());
        verify(productRepository, times(1)).findByBarCode(BAR_CODE);
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("should return product already registered")
    void saveProductCase2() {
        //Arrange
        ProductInsertDto productInsertDto = createProductInsertDTO(BAR_CODE);
        ProductEntity productEntity = new ProductEntity(productInsertDto);

        when(productRepository.findByBarCode(BAR_CODE)).thenReturn(Optional.of(productEntity));

        //Act && Assert
        assertThrows(
                ProductAlreadyRegisteredException.class,
                () -> productService.saveProduct(productInsertDto)
        );
        verify(productRepository, times(1)).findByBarCode(BAR_CODE);
    }

    @Test
    @DisplayName("should return category not found")
    void saveProductCase3() {
        //Arrange
        ProductInsertDto productInsertDto = createProductInsertDTO(BAR_CODE);

        when(productRepository.findByBarCode(BAR_CODE)).thenReturn(Optional.empty());
        when(categoryRepository.findById(productInsertDto.categoryId())).thenReturn(Optional.empty());

        //Act && Assert
        assertThrows(
                CategoryNotFoundException.class,
                () -> productService.saveProduct(productInsertDto)
        );
        verify(categoryRepository, times(1)).findById(productInsertDto.categoryId());
    }

    @Test
    @DisplayName("Should update product successfully")
    void updateProductCase1() {
        //Arrange
        Integer productId = 1;
        ProductInsertDto productInsertDTO = createProductInsertDTO(BAR_CODE);
        ProductEntity productEntity = new ProductEntity(productId, productInsertDTO);
        CategoryEntity categoryEntity = new CategoryEntity("TEST");
        when(productRepository.findByProductIdAndProductEnableTrue(1)).thenReturn(Optional.of(productEntity));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryEntity));

        //Act
        SuccessResponse successResponse = productService.updateProduct(productInsertDTO, productId);

        //Assert
        assertEquals("Product Updated Successfully", successResponse.message());
    }

    private ProductInsertDto createProductInsertDTO(String BAR_CODE) {
        return new ProductInsertDto(BAR_CODE,
                "TEST",
                1,
                "UNIT",
                new BigDecimal(10),
                new BigDecimal(20),
                new BigDecimal(100),
                new BigDecimal(10));
    }
}