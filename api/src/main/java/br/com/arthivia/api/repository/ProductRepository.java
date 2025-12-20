package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
    Optional<ProductEntity> findByBarCode(String barCode);

    void setEnabledFalseByProductId(Integer productId);
}
