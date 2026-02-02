package br.com.arthivia.api.infra.security;

import br.com.arthivia.api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Ignora preflight
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;

        // 1️⃣ Tenta pelo Authorization header (Insomnia)
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        // 2️⃣ Se não veio no header, tenta pelo cookie (Angular)
//        if (token == null && request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("authToken".equals(cookie.getName())) {
//                    token = cookie.getValue();
//                    break;
//                }
//            }
//        }

        System.out.println(request.getCookies());
        if (token == null && request.getCookies() != null) {
            System.out.println("🍪 TODOS COOKIES:");
            for (Cookie cookie : request.getCookies()) {
                System.out.println("  🍪 NOME: '" + cookie.getName() + "' | VALOR: '" +
                        (cookie.getValue() != null ? cookie.getValue().substring(0, Math.min(20, cookie.getValue().length())) + "..." : "NULL") + "'");
                if ("authToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    System.out.println("✅ TOKEN EXTRAÍDO: " + token);
                    break;
                }
            }
            System.out.println("🍪 TOKEN FINAL: " + token);
        }

        // 3️⃣ Se não tem token, segue sem autenticar
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4️⃣ Valida token
        String username;
        try {
            username = tokenService.validateToken(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        var userOpt = userRepository.findByUsernameAndEnableTrue(username);
        if (userOpt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        var user = userOpt.get();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}