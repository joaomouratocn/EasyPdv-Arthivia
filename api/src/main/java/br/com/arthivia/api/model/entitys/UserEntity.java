package br.com.arthivia.api.model.entitys;

import br.com.arthivia.api.util.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId;
    @Column(name = "name")
    String name;
    @Column(name = "login")
    String login;
    @Column(name = "pass_hash")
    String passHash;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    UserRole userRole;
    @Column(name = "enable")
    Boolean enable;
    @Column(name = "created_at", insertable = false)
    LocalDateTime createdAt;

    public UserEntity(String name, String login, String passHash, UserRole role, boolean enable) {
        this.name = name;
        this.login = login;
        this.passHash = passHash;
        this.userRole = role;
        this.enable = enable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRole == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("CHECKOUT"));
        } else {
            return List.of(new SimpleGrantedAuthority("CHECKOUT"));
        }
    }

    @Override
    public @Nullable String getPassword() {
        return passHash;
    }

    @Override
    public String getUsername() {
        return login;
    }
}
