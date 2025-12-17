package br.com.arthivia.api.infra.exceptions.custom;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User not found");
    }
}
