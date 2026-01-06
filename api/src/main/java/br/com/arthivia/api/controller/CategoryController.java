package br.com.arthivia.api.controller;

import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.CategoryInsertDto;
import br.com.arthivia.api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/save")
    ResponseEntity<SuccessResponse> saveCategory(@RequestBody @Valid CategoryInsertDto categoryInsertDto){
        var result = categoryService.saveCategory(categoryInsertDto);
        return ResponseEntity.ok(result);
    }
}
