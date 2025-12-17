package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByLoginAndEnableTrue(String normalizedLogin);
}
