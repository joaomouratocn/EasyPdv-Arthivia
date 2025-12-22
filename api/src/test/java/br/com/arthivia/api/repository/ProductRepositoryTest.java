package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.CategoryEntity;
import br.com.arthivia.api.model.entitys.ProductEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return empty when product not found by bar code")
    void findByBarCodeCase1() {
        Optional<ProductEntity> byBarCode = productRepository.findByBarCodeAndProductEnableTrue("1234567890");
        assertTrue(byBarCode.isEmpty());
    }

    @Test
    @DisplayName("Should return product when enable and found by bar code")
    void findByBarCodeCase2() {
        var BARCODE = "123456";
        CategoryEntity testCategory = new CategoryEntity("TEST CATEGORY");
        ProductEntity productEntity = new ProductEntity(BARCODE,
                "NAME",
                testCategory,
                new BigDecimal("2.00"),
                new BigDecimal("10.00"),
                new BigDecimal("100"),
                new BigDecimal("10"));
        entityManager.persist(testCategory);
        entityManager.persist(productEntity);
        entityManager.flush();

        Optional<ProductEntity> result = productRepository.findByBarCodeAndProductEnableTrue(BARCODE);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should set product enabled to false by product id")
    void saveEnableFalseByProductIdCase1() {
    }

    @Test
    @DisplayName("Should do nothing when product id not found")
    void saveEnableFalseByProductIdCase2() {
    }
}