package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.CategoryEntity;
import br.com.arthivia.api.model.entitys.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {}