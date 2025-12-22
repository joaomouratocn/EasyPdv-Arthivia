package br.com.arthivia.api.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
}

