package br.com.arthivia.api.infra.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "cookie")
public class CookieProperties {
    private boolean httpOnly;
    private boolean secure;
    private String sameSite;
}