package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.productEnable = false WHERE p.productId = :productId")
    void disableProduct(@Param("productId") Integer productId);

    Optional<ProductEntity> findByProductIdAndProductEnableTrue(Integer id);

    Optional<ProductEntity> findByBarCode(String barCode);

    Optional<ProductEntity> findByBarCodeAndProductEnableTrue(String barCode);

    List<ProductEntity> findAllByCategoryIdAndProductEnableTrue(@Param("categoryId") Integer categoryId);
}
