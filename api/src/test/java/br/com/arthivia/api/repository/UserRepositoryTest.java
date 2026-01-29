package br.com.arthivia.api.repository;

import br.com.arthivia.api.infra.exceptions.custom.UserNotFoundException;
import br.com.arthivia.api.model.entitys.UserEntity;
import br.com.arthivia.api.util.Enums.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        //Act
        var result = userRepository.findByUsernameAndEnableTrue("TEST");
        //Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should return empty when user disabled")
    void findByLoginAndEnableTrueCase2() {
        //Arrange
        var userEntity = new UserEntity("TEST", "TEST", "passHash", UserRole.ADMIN, false);
        entityManager.persist(userEntity);
        entityManager.flush();
        //Act
        var result = userRepository.findByUsernameAndEnableTrue("TEST");
        //Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should return user when enabled")
    void findByLoginAndEnableTrueCase3() {
        //Arrange
        var userEntity = new UserEntity("TEST", "TEST", "passHash", UserRole.ADMIN, true);
        entityManager.persist(userEntity);
        entityManager.flush();
        //Act
        UserDetails result = userRepository.findByUsernameAndEnableTrue("TEST").orElseThrow(UserNotFoundException::new);
        //Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should ignore disabled users for different username")
    void findByLoginAndEnableTrueCase4() {
        //Arrange
        var userEntity = new UserEntity("DIFFERENT", "DIFFERENT", "passHash", UserRole.ADMIN, false);
        entityManager.persist(userEntity);
        entityManager.flush();
        //Act
        var result = userRepository.findByUsernameAndEnableTrue("TEST");
        //Assert
        assertNotNull(result);
    }
}
