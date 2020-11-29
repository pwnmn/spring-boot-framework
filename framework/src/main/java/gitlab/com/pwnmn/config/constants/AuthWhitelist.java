package gitlab.com.pwnmn.config.constants;

/**
 * Default authentication whitelist for well known endpoints.
 */
public class AuthWhitelist {

    public static final String[] AUTH_WHITELIST_SWAGGER = {
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/api-docs",
            "/swagger-ui/**"
    };
    public static final String[] AUTH_WHITELIST_MISC = {
            "/actuator",
            "/actuator/**",
            "/webjars/**"
    };
}
