package gitlab.com.pwnmn.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import gitlab.com.pwnmn.config.model.CorsProperties;

import java.util.Arrays;

/**
 * CORS settings can be configured separately.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "framework.cors.enabled", havingValue = "true", matchIfMissing = true)
public class CorsConfig implements WebMvcConfigurer {

    private final CorsProperties corsProps;

    public CorsConfig(CorsProperties corsProps) {
        this.corsProps = corsProps;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.debug("Cors origin set to '{}'", corsProps.getDomains());
        registry
                .addMapping("/**")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.HEAD.name()
                )
                .allowedOrigins(
                        Arrays.toString(
                                corsProps.getDomains().toArray()
                        )
                );
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOrigins(
                corsProps.getDomains()
        );
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
