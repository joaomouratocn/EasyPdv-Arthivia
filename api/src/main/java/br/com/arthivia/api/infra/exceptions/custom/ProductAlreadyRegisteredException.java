package br.com.arthivia.api.infra.exceptions.custom;

public class ProductAlreadyRegisteredException extends RuntimeException{
    public ProductAlreadyRegisteredException() {
        super("Product Already Registered");
    }
}
