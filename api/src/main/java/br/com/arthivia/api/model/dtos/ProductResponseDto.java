package br.com.arthivia.api.model.dtos;

import br.com.arthivia.api.model.entitys.ProductEntity;

import java.math.BigDecimal;

public record ProductResponseDto(
        Integer prodId,
        String barcode,
        String productName,
        CategoryResponseDto category,
        String UnitMeasure,
        BigDecimal costPrice,
        BigDecimal salePrice,
        BigDecimal amount,
        BigDecimal amountMin,
        Boolean enable
){
    public ProductResponseDto(ProductEntity productEntity, CategoryResponseDto categoryResponseDto) {
        this(productEntity.getProductId(),
                productEntity.getBarCode(),
                productEntity.getProductName(),
                categoryResponseDto,
                productEntity.getUnitMeasure(),
                productEntity.getCostPrice(),
                productEntity.getSalePrice(),
                productEntity.getAmount(),
                productEntity.getMinAmount(),
                productEntity.getProductEnable());
    }
}
