package br.com.arthivia.api.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor()
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    @Column(name = "bar_code", unique = true)
    private String barCode;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "unit_measure", nullable = false)
    private String unitMeasure;
    @Column(name = "cost_price", nullable = false)
    private BigDecimal costPrice;
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "min_amount")
    private BigDecimal minAmount;
    @Column(name = "product_enabled")
    private Boolean productEnabled;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
