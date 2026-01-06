package br.com.arthivia.api.infra.exceptions.custom;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists() {
        super("User already exists");
    }
}
