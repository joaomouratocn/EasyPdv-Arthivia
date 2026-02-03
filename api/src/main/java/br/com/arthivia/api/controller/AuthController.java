package br.com.arthivia.api.controller;

import br.com.arthivia.api.infra.security.CookieProperties;
import br.com.arthivia.api.model.SuccessResponse;
import br.com.arthivia.api.model.dtos.AuthRequestDto;
import br.com.arthivia.api.model.dtos.AuthResponseDto;
import br.com.arthivia.api.model.dtos.UserInsertDto;
import br.com.arthivia.api.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;
    private final CookieProperties cookieProperties;

    public AuthController(AuthService authService, CookieProperties cookieProperties) {
        this.authService = authService;
        this.cookieProperties = cookieProperties;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        var result = authService.auth(authRequestDto);

        ResponseCookie cookie = generateToken(result.token());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(result.authResponseDto());
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@CookieValue(name = "authToken", required = false) String token) {
        System.out.println(token);

        return ResponseEntity.ok("token");
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse> logout(HttpServletResponse response) {
        removeToken(response);

        return ResponseEntity.ok(new SuccessResponse("Success"));
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody @Valid UserInsertDto userInsertDto) {
        var result = authService.register(userInsertDto);
        return ResponseEntity.ok(result);
    }

    private static void removeToken(HttpServletResponse response) {
        Cookie cookie = new Cookie("authToken", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private ResponseCookie generateToken(String token) {
        return ResponseCookie.from("authToken", token)
                .httpOnly(cookieProperties.isHttpOnly())
                .sameSite(cookieProperties.getSameSite())
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();
    }
}
