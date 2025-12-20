package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.ProductEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

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
        Optional<ProductEntity> byBarCode = productRepository.findByBarCode("1234567890");
        assertTrue(byBarCode.isEmpty());
    }

    @Test
    @DisplayName("Should return product when found by bar code")
    void findByBarCodeCase2() {
    }

    @Test
    @DisplayName("Should set product enabled to false by product id")
    void setEnabledFalseByProductIdCase1() {
    }

    @Test
    @DisplayName("Should do nothing when product id not found")
    void setEnabledFalseByProductIdCase2() {
    }
}