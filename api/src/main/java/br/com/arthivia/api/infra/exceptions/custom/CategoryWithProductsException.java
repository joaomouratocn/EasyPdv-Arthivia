package br.com.arthivia.api.infra.exceptions.custom;

public class CategoryWithProductsException extends RuntimeException {
    public CategoryWithProductsException() {
        super("Cannot delete category with associated products.");
    }
}
