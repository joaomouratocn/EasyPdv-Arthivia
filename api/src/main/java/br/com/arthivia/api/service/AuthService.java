package br.com.arthivia.api.service;

import br.com.arthivia.api.infra.exceptions.custom.UserNotFoundException;
import br.com.arthivia.api.infra.security.TokenService;
import br.com.arthivia.api.model.dtos.AuthRequestDto;
import br.com.arthivia.api.model.dtos.AuthResponseDto;
import br.com.arthivia.api.model.entitys.UserEntity;
import br.com.arthivia.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

        userRepository.findByLoginAndEnableTrue(normalizedLogin)
                .orElseThrow(UserNotFoundException::new);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(normalizedLogin, authRequestDto.password());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserEntity userEntity = (UserEntity) authenticate.getPrincipal();

        assert userEntity != null;
        String token = tokenService.generateToken(userEntity);

        boolean alterPass = defaultPass.equals(authRequestDto.password());

        return new AuthResponseDto(userEntity.getName(), token, alterPass);
    }
}
