package br.com.arthivia.api.controller;

import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.AuthRequestDto;
import br.com.arthivia.api.model.dtos.AuthResponseDto;
import br.com.arthivia.api.model.dtos.UserInsertDto;
import br.com.arthivia.api.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        var result = authService.auth(authRequestDto);

        ResponseCookie cookie = generateTokenCookie(result.token(), null);

        assert cookie != null;
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(result.authResponseDto());
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        generateTokenCookie(null, response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody @Valid UserInsertDto userInsertDto) {
        var result = authService.register(userInsertDto);
        return ResponseEntity.ok(result);
    }

    private ResponseCookie generateTokenCookie(String token, HttpServletResponse response) {
        if (token == null || token.isEmpty()) {
            Cookie cookie = new Cookie("authToken", "");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return null;
        } else {
            ResponseCookie cookie = ResponseCookie.from("authToken", token)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .build();
            return cookie;
        }
    }
}
