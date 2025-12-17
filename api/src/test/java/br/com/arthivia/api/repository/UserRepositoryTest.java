package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.UserEntity;
import br.com.arthivia.api.util.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return empty when user not found")
    void findByLoginAndEnableTrueCase1() {
        Optional<UserEntity> result = userRepository.findByLoginAndEnableTrue("TEST");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty when user enable false")
    void findByLoginAndEnableTrueCase2(){
        var userEntity = new UserEntity("TEST", "TEST", "passHash", UserRole.ADMIN, false);

        entityManager.persist(userEntity);

        Optional<UserEntity> result = userRepository.findByLoginAndEnableTrue("TEST");
        assertTrue(result.isEmpty());
    }
}