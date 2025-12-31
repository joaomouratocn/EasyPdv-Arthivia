package br.com.arthivia.api.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductInsertDto(
    @NotNull(message = "Invalid barcode")
    @NotBlank(message = "Invalid barcode")
    String barcode,
    @NotNull(message = "Invalid product name")
    @NotBlank(message = "Invalid product name")
    String productName,
    @NotNull(message = "Invalid category")
    Integer categoryId,
    @NotNull(message = "Invalid unit measure")
    @NotBlank(message = "Invalid unit measure")
    String UnitMeasure,
    @NotNull(message = "Invalid cost price")
    BigDecimal costPrice,
    @NotNull(message = "Invalid sale price")
    BigDecimal salePrice,
    @NotNull(message = "Invalid amount")
    BigDecimal amount,
    @NotNull(message = "Invalid amount min")
    BigDecimal amountMin
){}
