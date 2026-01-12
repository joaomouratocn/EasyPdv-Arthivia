package br.com.arthivia.api.service;

import br.com.arthivia.api.infra.exceptions.custom.CategoryNotFoundException;
import br.com.arthivia.api.infra.exceptions.custom.ProductAlreadyRegisteredException;
import br.com.arthivia.api.infra.exceptions.custom.ProductNotFoundException;
import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.CategoryResponseDto;
import br.com.arthivia.api.model.dtos.ProductInsertDto;
import br.com.arthivia.api.model.dtos.ProductResponseDto;
import br.com.arthivia.api.model.entitys.CategoryEntity;
import br.com.arthivia.api.model.entitys.ProductEntity;
import br.com.arthivia.api.repository.CategoryRepository;
import br.com.arthivia.api.repository.ProductRepository;
import br.com.arthivia.api.util.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public SuccessResponse saveProduct(ProductInsertDto productInsertDto) {
        Optional<ProductEntity> byBarCode = productRepository.findByBarCode(productInsertDto.barcode());

        if (byBarCode.isPresent()) {
            throw new ProductAlreadyRegisteredException();
        }

        categoryRepository.findById(productInsertDto.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        ProductEntity registerProduct = new ProductEntity(productInsertDto);

        System.out.println(registerProduct);

        productRepository.save(registerProduct);

        return new SuccessResponse("Product Saved successfully");
    }

    public SuccessResponse updateProduct(ProductInsertDto productInsertDto, Integer productId) {
        productRepository.findByProductIdAndProductEnableTrue(productId).orElseThrow(ProductNotFoundException::new);

        categoryRepository.findById(productInsertDto.categoryId()).orElseThrow(CategoryNotFoundException::new);

        ProductEntity productEntity = new ProductEntity(productId, productInsertDto);

        productRepository.save(productEntity);

        return new SuccessResponse("Product Updated Successfully");
    }

    public SuccessResponse deleteProduct(Integer productId){
        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        productRepository.disableProduct(productId);

        return new SuccessResponse("Product delete successfully");
    }

    public ProductResponseDto getProductById(Integer prodId) {
        ProductEntity productEntity = productRepository.findByProductIdAndProductEnableTrue(prodId).orElseThrow(ProductNotFoundException::new);

        CategoryEntity category = categoryRepository.findById(productEntity.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(category);

        return new ProductResponseDto(productEntity, categoryResponseDto);
    }

    public List<ProductResponseDto> getProductAll() {
        return productRepository.findAll().stream().map(productEntity -> {
            Optional<CategoryEntity> category = categoryRepository.findById(productEntity.getCategoryId());
            if (category.isEmpty()) {
                throw new RuntimeException("Error on load Category");
            }

            var categoryResponse = new CategoryResponseDto(category.get());

            return new ProductResponseDto(productEntity, categoryResponse);
        }).toList();
    }

    public List<ProductResponseDto> getProductByName(String name) {
        var normalizedName = Util.normalizeUpper(name);
        return productRepository.findByProductNameContainingAndProductEnableTrue(normalizedName).stream().map(productEntity -> {
          Optional<CategoryEntity> category = categoryRepository.findById(productEntity.getCategoryId());
          if(category.isEmpty()){
              throw new RuntimeException("Error on load category");
          }

          var categoriesResponse = new CategoryResponseDto(category.get());

          return new ProductResponseDto(productEntity, categoriesResponse);
        }).toList();
    }

    public ProductResponseDto getProductByBarcode(@Valid String barcode) {
        ProductEntity productEntity = productRepository.findByBarCodeAndProductEnableTrue(barcode).orElseThrow(ProductNotFoundException::new);
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(productEntity.getCategoryId());

        if(categoryEntity.isEmpty()){
            throw new RuntimeException("Error on load category");
        }

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(categoryEntity.get());

        return new ProductResponseDto(productEntity, categoryResponseDto);
    }
}
