package br.com.arthivia.api.util;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }
}
