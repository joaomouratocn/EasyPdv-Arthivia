package br.com.arthivia.api.model.entitys;

import br.com.arthivia.api.model.dtos.CategoryInsertDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer categoryId;
    @Column(name = "name")
    String name;

    public CategoryEntity(String categoryName) {
        this.name = categoryName;
    }

    public CategoryEntity(CategoryInsertDto categoryInsertDto) {
        this.name = categoryInsertDto.categoryName();
    }
}

