package br.com.arthivia.api.model.entitys;

import br.com.arthivia.api.util.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class UserEntity {
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
}
