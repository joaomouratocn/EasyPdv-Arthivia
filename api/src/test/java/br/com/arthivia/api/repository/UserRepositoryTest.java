package br.com.arthivia.api.repository;

import br.com.arthivia.api.model.entitys.UserEntity;
import br.com.arthivia.api.util.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
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
    @DisplayName("Should return empty when user disabled")
    void findByLoginAndEnableTrueCase2() {
        var userEntity = new UserEntity("TEST", "TEST", "passHash", UserRole.ADMIN, false);
        entityManager.persist(userEntity);
        entityManager.flush(); // ✅ CRÍTICO: garante que query vê o dado

        Optional<UserEntity> result = userRepository.findByLoginAndEnableTrue("TEST");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return user when enabled")
    void findByLoginAndEnableTrueCase3() {
        var userEntity = new UserEntity("TEST", "TEST", "passHash", UserRole.ADMIN, true);
        entityManager.persist(userEntity);
        entityManager.flush();

        Optional<UserEntity> result = userRepository.findByLoginAndEnableTrue("TEST");
        assertTrue(result.isPresent());
        assertEquals("TEST", result.get().getLogin()); // valida conteúdo
    }

    @Test
    @DisplayName("Should ignore disabled users for different login")
    void findByLoginAndEnableTrueCase4() {
        var userEntity = new UserEntity("DIFFERENT", "DIFFERENT", "passHash", UserRole.ADMIN, false);
        entityManager.persist(userEntity);
        entityManager.flush();

        Optional<UserEntity> result = userRepository.findByLoginAndEnableTrue("TEST");
        assertTrue(result.isEmpty());
    }
}
