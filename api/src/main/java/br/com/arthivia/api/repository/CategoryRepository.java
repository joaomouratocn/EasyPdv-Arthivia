package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}