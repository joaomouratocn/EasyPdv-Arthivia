package br.com.arthivia.api.service;

import br.com.arthivia.api.infra.security.TokenService;
import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.AuthRequestDto;
import br.com.arthivia.api.model.dtos.AuthResponseDto;
import br.com.arthivia.api.model.dtos.UserInsertDto;
import br.com.arthivia.api.model.entitys.UserEntity;
import br.com.arthivia.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthResponseDto auth(AuthRequestDto authRequestDto) {
        String normalizedLogin = authRequestDto.login().toUpperCase();

        var usernamePassword = new UsernamePasswordAuthenticationToken(normalizedLogin, authRequestDto.password());

        Authentication authenticate = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken(authenticate);

        return new AuthResponseDto("test", token, false);
    }

    public SuccessResponse register(UserInsertDto userInsertDto) {
        String normalizedLogin = userInsertDto.login().toUpperCase();

        userRepository.findByLoginAndEnableTrue(normalizedLogin);

        String passHash = new BCryptPasswordEncoder().encode(userInsertDto.password());
        UserEntity userEntity = new UserEntity(userInsertDto, passHash);

        userRepository.save(userEntity);

        return new SuccessResponse("User registered successfully");
    }
}
