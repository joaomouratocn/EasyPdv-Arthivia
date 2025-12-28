package br.com.arthivia.api.service;

import br.com.arthivia.api.infra.exceptions.custom.UserNotFoundException;
import br.com.arthivia.api.infra.security.TokenService;
import br.com.arthivia.api.model.dtos.AuthRequestDto;
import br.com.arthivia.api.model.dtos.AuthResponseDto;
import br.com.arthivia.api.model.entitys.UserEntity;
import br.com.arthivia.api.repository.UserRepository;
import br.com.arthivia.api.util.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private static final String DEFAULT_PASS = "12345";

    @Mock
    TokenService tokenService;
    @Mock
    UserRepository userRepository;
    @Mock
    AuthenticationManager authenticationManager;

    AuthService authService;  // ← Declare sem @InjectMocks

    @BeforeEach
    void setUp() {
        authService = new AuthService(DEFAULT_PASS, userRepository, tokenService, authenticationManager);
    }

    @Test
    @DisplayName("Should throw error when user not found")
    void authCase1() {
        //Arrange
        var authRequestDto = new AuthRequestDto("TEST", DEFAULT_PASS);
        when(userRepository.findByLoginAndEnableTrue("TEST")).thenReturn(Optional.empty());

        //Act && Assert
        assertThrows(UserNotFoundException.class, () -> {
            authService.auth(authRequestDto);
        });

        //Verify
        verify(authenticationManager, never()).authenticate(any());
        verify(userRepository, times(1)).findByLoginAndEnableTrue("TEST");
    }

    @Test
    @DisplayName("Should authenticate successfully with valid credentials")
    void authCase2() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        var userEntity = new UserEntity("TEST", "TEST", DEFAULT_PASS, UserRole.ADMIN, true);
        var authRequestDto = new AuthRequestDto("TEST", DEFAULT_PASS);

        when(userRepository.findByLoginAndEnableTrue("TEST")).thenReturn(Optional.of(userEntity));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEntity);
        when(tokenService.generateToken(userEntity)).thenReturn("TEST_TOKEN");

        // Act
        AuthResponseDto responseDto = authService.auth(authRequestDto);

        // Assert
        assertEquals("TEST", responseDto.name());
        assertEquals("TEST_TOKEN", responseDto.token());
        verify(userRepository, times(1)).findByLoginAndEnableTrue("TEST");
        verify(authenticationManager, times(1)).authenticate(any());
        verify(tokenService, times(1)).generateToken(userEntity);
    }

    @Test
    @DisplayName("Should return user info and token on successful authentication")
    void authCase3() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        var userEntity = new UserEntity("TEST", "TEST", DEFAULT_PASS, UserRole.ADMIN, true);
        var authRequestDto = new AuthRequestDto("TEST", DEFAULT_PASS);

        when(userRepository.findByLoginAndEnableTrue("TEST")).thenReturn(Optional.of(userEntity));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userEntity);
        when(tokenService.generateToken(userEntity)).thenReturn("TEST_TOKEN");

        // Act
        AuthResponseDto responseDto = authService.auth(authRequestDto);

        // Assert
        assertEquals("TEST", responseDto.name());
        assertEquals("TEST_TOKEN", responseDto.token());
        verify(userRepository, times(1)).findByLoginAndEnableTrue("TEST");
        verify(authenticationManager, times(1)).authenticate(any());
        verify(tokenService, times(1)).generateToken(userEntity);
    }
}
