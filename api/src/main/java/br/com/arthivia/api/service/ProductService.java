package br.com.arthivia.api.service;

import br.com.arthivia.api.infra.exceptions.custom.CategoryNotFoundException;
import br.com.arthivia.api.infra.exceptions.custom.ProductAlreadyRegisteredException;
import br.com.arthivia.api.infra.exceptions.custom.ProductNotFoundException;
import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.ProductInsertDto;
import br.com.arthivia.api.model.entitys.ProductEntity;
import br.com.arthivia.api.repository.CategoryRepository;
import br.com.arthivia.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public SuccessResponse saveProduct(ProductInsertDto productInsertDto) {
        Optional<ProductEntity> productEntity = productRepository.findByBarCode(productInsertDto.barcode());

        if (productEntity.isPresent()) throw new ProductAlreadyRegisteredException();

        categoryRepository.findById(productInsertDto.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        ProductEntity registerProduct = new ProductEntity(productInsertDto);

        productRepository.save(registerProduct);

        return new SuccessResponse("Product Saved successfully");
    }

    public SuccessResponse updateProduct(ProductInsertDto productInsertDto, Integer productId){
        productRepository.findByProductIdAndProductEnableTrue(productId).orElseThrow(ProductNotFoundException::new);

        categoryRepository.findById(productInsertDto.categoryId()).orElseThrow(CategoryNotFoundException::new);

        ProductEntity productEntity = new ProductEntity(productId, productInsertDto);

        productRepository.save(productEntity);

        return new SuccessResponse("Product Updated Successfully");
    }
}
