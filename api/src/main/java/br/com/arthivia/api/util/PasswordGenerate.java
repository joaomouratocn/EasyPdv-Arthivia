package br.com.arthivia.api.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerate {
    public static void main(String[] args) {
        String defaultPass = "12345";

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String hash = encoder.encode(defaultPass);

        System.out.println("Senha: " + defaultPass);
        System.out.println("Hash BCrypt: " + hash);
    }
}