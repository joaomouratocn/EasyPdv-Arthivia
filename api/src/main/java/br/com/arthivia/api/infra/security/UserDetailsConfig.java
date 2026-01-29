package br.com.arthivia.api.infra.security;

import br.com.arthivia.api.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository
                .findByUsernameAndEnableTrue(username.toUpperCase())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
