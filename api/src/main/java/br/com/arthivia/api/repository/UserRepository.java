package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
