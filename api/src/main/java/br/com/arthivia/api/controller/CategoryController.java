package br.com.arthivia.api.controller;

import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.CategoryInsertDto;
import br.com.arthivia.api.model.dtos.CategoryResponseDto;
import br.com.arthivia.api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        var result = categoryService.getAllCategories();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/insert")
    ResponseEntity<SuccessResponse> saveCategory(@RequestBody @Valid CategoryInsertDto categoryInsertDto) {
        var result = categoryService.saveCategory(categoryInsertDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update/{categoryId}")
    ResponseEntity<SuccessResponse> updateCategory(@RequestBody @Valid CategoryInsertDto categoryInsertDto,
                                                   @PathVariable Integer categoryId) {
        var result = categoryService.updateCategory(categoryInsertDto, categoryId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete/{categoryId}")
    ResponseEntity<SuccessResponse> deleteCategory(@PathVariable Integer categoryId) {
        var result = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{categoryId}")
    ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Integer categoryId) {
        var result = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(result);
    }
}
