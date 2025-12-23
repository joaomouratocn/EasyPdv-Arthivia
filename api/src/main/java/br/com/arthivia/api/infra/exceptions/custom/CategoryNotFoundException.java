package br.com.arthivia.api.infra.exceptions.custom;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(){
        super("Category not found");
    }
}
