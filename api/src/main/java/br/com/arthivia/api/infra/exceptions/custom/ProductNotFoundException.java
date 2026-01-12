package br.com.arthivia.api.infra.exceptions.custom;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super("Product Not Found");
    }
}
