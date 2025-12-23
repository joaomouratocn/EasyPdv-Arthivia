package br.com.arthivia.api.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "product")
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @Column(name = "unit_measure")
    private String unitMeasure;
    @Column(name = "cost_price", nullable = false)
    private BigDecimal costPrice;
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "min_amount")
    private BigDecimal minAmount;
    @Column(name = "product_enable")
    private Boolean productEnable;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ProductEntity(String barCode,
                         String name,
                         CategoryEntity category,
                         BigDecimal costPrice,
                         BigDecimal salePrice,
                         BigDecimal amount,
                         BigDecimal minAmount,
                         Boolean productEnable,
                         LocalDateTime createdAt
    ) {
        this.barCode = barCode;
        this.productName = name;
        this.category = category;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.amount = amount;
        this.minAmount = minAmount;
        this.productEnable = true;
        this.createdAt = createdAt;
    }
}
