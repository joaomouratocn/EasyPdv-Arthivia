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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Transactional // Garante rollback automático após cada teste
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
        Integer productId = createProduct("1234567");

        Optional<ProductEntity> result = productRepository.findByBarCodeAndProductEnableTrue("1234567");
        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getProductId()); // Verificação extra
    }

    @Test
    @DisplayName("Should set product enabled to false by product id")
    void saveEnableFalseByProductIdCase1() {
        Integer id = createProduct("1234567");
        productRepository.disableProduct(id);

        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        assertFalse(productEntity.getProductEnable());
        assertNotNull(productEntity); // Garante que o produto existe
    }

    private Integer createProduct(String barcode) {
        CategoryEntity testCategory = new CategoryEntity("TEST CATEGORY");
        ProductEntity productEntity = new ProductEntity(barcode,
                "NAME",
                testCategory,
                new BigDecimal("2.00"),
                new BigDecimal("10.00"),
                new BigDecimal("100"),
                new BigDecimal("10"),
                true,
                LocalDateTime.now()
        );
        entityManager.persist(testCategory);
        entityManager.persist(productEntity);
        entityManager.flush();
        return productEntity.getProductId();
    }
}
