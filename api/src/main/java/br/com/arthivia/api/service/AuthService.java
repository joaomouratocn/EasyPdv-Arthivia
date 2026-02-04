package br.com.arthivia.api.service;

import br.com.arthivia.api.infra.exceptions.custom.UserAlreadyExists;
import br.com.arthivia.api.infra.exceptions.custom.UserNotFoundException;
import br.com.arthivia.api.infra.security.TokenService;
import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.AuthRequestDto;
import br.com.arthivia.api.model.dtos.AuthResponseDto;
import br.com.arthivia.api.model.dtos.ResultAuth;
import br.com.arthivia.api.model.dtos.UserInsertDto;
import br.com.arthivia.api.model.entitys.UserEntity;
import br.com.arthivia.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    String defaultPass;
    UserRepository userRepository;
    TokenService tokenService;
    AuthenticationManager authenticationManager;

    public AuthService(
            @Value("${api.secret.pass.default}") String defaultPass,
            UserRepository userRepository,
            TokenService tokenService,
            AuthenticationManager authenticationManager
    ) {
        this.defaultPass = defaultPass;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public ResultAuth auth(AuthRequestDto authRequestDto) {
        String normalizedLogin = authRequestDto.username().toUpperCase();

        var usernamePassword = new UsernamePasswordAuthenticationToken(normalizedLogin, authRequestDto.password());

        Authentication authenticate = authenticationManager.authenticate(usernamePassword);

        var user = (UserEntity) authenticate.getPrincipal();

        assert user != null;
        var token = tokenService.generateToken(user, false);
        var refreshToken = tokenService.generateToken(user, true);

        var shouldAlterPass = authRequestDto.password().equals(defaultPass);

        var responseAuth = new AuthResponseDto(user.getName(), token, shouldAlterPass);

        return new ResultAuth(refreshToken, responseAuth);
    }

    public AuthResponseDto refresh(String token) {
        String username = tokenService.validateToken(token);

        UserEntity user = (UserEntity) userRepository.findByUsernameAndEnableTrue(username)
                .orElseThrow(UserNotFoundException::new);

        String newToken = tokenService.generateToken(user, false);

        return new AuthResponseDto(user.getName(), newToken, false);
    }

    public SuccessResponse register(UserInsertDto userInsertDto) {
        String normalizedLogin = userInsertDto.username().toUpperCase();

        Optional<UserDetails> userAlreadyExists = userRepository.findByUsernameAndEnableTrue(normalizedLogin);

        if (userAlreadyExists.isPresent()) {
            throw new UserAlreadyExists();
        }

        String passHash = new BCryptPasswordEncoder().encode(userInsertDto.password());
        UserEntity userEntity = new UserEntity(userInsertDto, passHash);

        userRepository.save(userEntity);

        return new SuccessResponse("User registered successfully");
    }
}
