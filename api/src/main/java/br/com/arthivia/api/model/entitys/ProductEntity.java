package br.com.arthivia.api.model.entitys;

import br.com.arthivia.api.model.dtos.CategoryResponseDto;
import br.com.arthivia.api.model.dtos.ProductInsertDto;
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
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;
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
                         Integer categoryId,
                         String unitMeasure,
                         BigDecimal costPrice,
                         BigDecimal salePrice,
                         BigDecimal amount,
                         BigDecimal minAmount,
                         Boolean productEnable
    ) {
        this.barCode = barCode;
        this.productName = name;
        this.categoryId = categoryId;
        this.unitMeasure = unitMeasure;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.amount = amount;
        this.minAmount = minAmount;
        this.productEnable = productEnable;
        this.createdAt = LocalDateTime.now();
    }

    public ProductEntity(ProductInsertDto productInsertDto) {
        this.barCode = productInsertDto.barcode();
        this.productName = productInsertDto.productName();
        this.categoryId = productInsertDto.categoryId();
        this.unitMeasure = productInsertDto.UnitMeasure();
        this.costPrice = productInsertDto.salePrice();
        this.salePrice = productInsertDto.salePrice();
        this.amount = productInsertDto.amount();
        this.minAmount = productInsertDto.amountMin();
        this.productEnable = true;
        this.createdAt = LocalDateTime.now();
    }

    public ProductEntity(Integer prodId, ProductInsertDto productInsertDto) {
        this.productId = prodId;
        this.barCode = productInsertDto.barcode();
        this.productName = productInsertDto.productName();
        this.categoryId = productInsertDto.categoryId();
        this.unitMeasure = productInsertDto.UnitMeasure();
        this.costPrice = productInsertDto.salePrice();
        this.salePrice = productInsertDto.salePrice();
        this.amount = productInsertDto.amount();
        this.minAmount = productInsertDto.amountMin();
        this.productEnable = true;
        this.createdAt = LocalDateTime.now();
    }
}