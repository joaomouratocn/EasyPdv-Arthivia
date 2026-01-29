package br.com.arthivia.api.controller;

import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.AuthRequestDto;
import br.com.arthivia.api.model.dtos.AuthResponseDto;
import br.com.arthivia.api.model.dtos.UserInsertDto;
import br.com.arthivia.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        var token = authService.auth(authRequestDto);

        ResponseCookie cookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true) // somente HTTPS
                .sameSite("Strict") // ou "Lax"
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Success");
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody @Valid UserInsertDto userInsertDto) {
        var result = authService.register(userInsertDto);
        return ResponseEntity.ok(result);
    }
}
