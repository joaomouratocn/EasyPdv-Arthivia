package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.CategoryEntity;
import br.com.arthivia.api.model.entitys.ProductEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@Transactional // Garante rollback automático após cada teste
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    CategoryEntity testCategory = new CategoryEntity("TEST CATEGORY");

    @Test
    @DisplayName("Should return empty when product not found by bar code")
    void findByBarCodeCase1() {
        //Act
        Optional<ProductEntity> byBarCode = productRepository.findByBarCodeAndProductEnableTrue("1234567890");
        //Assert
        assertTrue(byBarCode.isEmpty());
    }

    @Test
    @DisplayName("Should return product when enable and found by bar code")
    void findByBarCodeCase2() {
        //Arrange
        Integer productId = createProduct("1234567", true, testCategory);
        //Act
        Optional<ProductEntity> result = productRepository.findByBarCodeAndProductEnableTrue("1234567");
        //Assert
        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getProductId());
    }

    @Test
    @Transactional
    @DisplayName("Should set product enabled to false by product id")
    void saveEnableFalseByProductIdCase1() {
        //Arrange
        Integer id = createProduct("1234567", true, testCategory);
        //Act
        productRepository.disableProduct(id);
        entityManager.clear();
        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        //Assert
        assertFalse(productEntity.getProductEnable());
        assertNotNull(productEntity);
    }

    @Test
    @DisplayName("Should return all enabled products by category id")
    void testFindAllByCategoryCategoryIdAndProductEnableTrueCase1() {
        //Arrange
        entityManager.persist(testCategory);
        createProduct("1234567", true, testCategory);
        createProduct("7654321", false, testCategory);

        //Act
        var result = productRepository.findAllByCategoryCategoryIdAndProductEnableTrue(testCategory.getCategoryId());

        //Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should return empty when products enable false")
    void testFindAllByCategoryCategoryIdAndProductEnableTrueCase2() {
        //Arrange
        entityManager.persist(testCategory);
        createProduct("1234567", false, testCategory);
        createProduct("7654321", false, testCategory);

        //Act
        var result = productRepository.findAllByCategoryCategoryIdAndProductEnableTrue(testCategory.getCategoryId());

        //Assert
        assertThat(result).isEmpty();
    }

    private Integer createProduct(String barcode, Boolean productEnable, CategoryEntity testCategory) {
        ProductEntity productEntity = new ProductEntity(barcode,
                "NAME",
                testCategory,
                new BigDecimal("2.00"),
                new BigDecimal("10.00"),
                new BigDecimal("100"),
                new BigDecimal("10"),
                productEnable,
                LocalDateTime.now()
        );
        entityManager.persist(testCategory);
        entityManager.persist(productEntity);
        entityManager.flush();
        return productEntity.getProductId();
    }
}
