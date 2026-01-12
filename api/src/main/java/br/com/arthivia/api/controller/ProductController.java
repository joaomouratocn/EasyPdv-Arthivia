package br.com.arthivia.api.controller;

import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.ProductInsertDto;
import br.com.arthivia.api.model.dtos.ProductResponseDto;
import br.com.arthivia.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/getall")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        var result = productService.getProductAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/save")
    public ResponseEntity<SuccessResponse> saveProduct(@RequestBody @Valid ProductInsertDto productInsertDto){
        var result = productService.saveProduct(productInsertDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update/{prodId}")
    public ResponseEntity<SuccessResponse> updateProduct(@RequestBody @Valid ProductInsertDto productInsertDto, @PathVariable @Valid Integer prodId){
        var result = productService.updateProduct(productInsertDto, prodId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{prodId}")
    public ResponseEntity<SuccessResponse> deleteProduct(@PathVariable @Valid Integer prodId){
        var result = productService.deleteProduct(prodId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byid/{prodId}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable @Valid Integer prodId){
        var result = productService.getProductById(prodId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<List<ProductResponseDto>> getByName(@PathVariable @Valid String name){
        var result = productService.getProductByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/bybarcode/{barcode}")
    public ResponseEntity<ProductResponseDto> getByBarcode(@PathVariable @Valid String barcode){
        var result = productService.getProductByBarcode(barcode);
        return ResponseEntity.ok(result);
    }
}
