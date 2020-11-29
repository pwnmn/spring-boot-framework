package gitlab.com.pwnmn.config.security;

import gitlab.com.pwnmn.config.constants.AuthWhitelist;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import gitlab.com.pwnmn.config.model.FrameworkSecurityProperties;

import java.security.Principal;

import static java.util.Objects.isNull;

@Slf4j
@EnableWebSecurity
@ComponentScan(
        basePackageClasses = KeycloakSecurityComponents.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX, pattern = "org.keycloak.adapters.springsecurity.management.HttpSessionManager"
        )
)
@ConditionalOnProperty(name = "framework.security.enabled", havingValue = "true", matchIfMissing = true)
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    private final FrameworkSecurityProperties securityProps;
    private final KeycloakClientRequestFactory keycloakClientRequestFactory;
    @Value("${framework.swagger.enabled:true}")
    private boolean swaggerEnabled;

    public SecurityConfig(KeycloakClientRequestFactory keycloakClientRequestFactory,
                          FrameworkSecurityProperties securityProps) {
        this.keycloakClientRequestFactory = keycloakClientRequestFactory;
        this.securityProps = securityProps;
        //to use principal and authentication together with @async
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        log.info("Initializing security configuration");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        var whitelist = securityProps.getWhitelist();
        var ignoredPaths = web.ignoring();

        whitelist.forEach(
                whiteListElem -> {
                    log.info("Adding to web security whitelist - Method {}, URL {}", whiteListElem.getMethod(), whiteListElem.getUrl());
                    var method = whiteListElem.getMethod();
                    if(isNull(method) || "".equals(method)) {
                        ignoredPaths.antMatchers(
                                whiteListElem.getUrl()
                        );
                    } else {
                        ignoredPaths.antMatchers(
                                HttpMethod.valueOf(whiteListElem.getMethod()),
                                whiteListElem.getUrl()
                        );
                    }
                }
        );
    }

    /**
     * Defines authorization constraings
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        log.info("Security is enabled!");
        var whitelist = securityProps.getWhitelist();
        if(whitelist.isEmpty()) {
            log.info("No security whitelist supplied");
        }

        http
                .cors()
                .and()
                .anonymous()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy());
        var authorizeRequests = http.authorizeRequests();
        authorizeRequests.antMatchers(AuthWhitelist.AUTH_WHITELIST_MISC).permitAll();
        if(swaggerEnabled) {
            authorizeRequests.antMatchers(AuthWhitelist.AUTH_WHITELIST_SWAGGER).permitAll();
        }
        whitelist.forEach(
                whiteListElem -> {
                    log.info("Adding to http security whitelist - Method {}, URL {}", whiteListElem.getMethod(), whiteListElem.getUrl());
                    var method = whiteListElem.getMethod();
                    if(isNull(method) || "".equals(method)) {
                        authorizeRequests.antMatchers(
                                whiteListElem.getUrl()
                        ).permitAll();
                    } else {
                        authorizeRequests.antMatchers(
                                HttpMethod.valueOf(whiteListElem.getMethod()),
                                whiteListElem.getUrl()
                        ).permitAll();
                    }
                }
        );
        authorizeRequests.anyRequest().authenticated();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate() {
        return new KeycloakRestTemplate(keycloakClientRequestFactory);
    }

    /**
     * registers the Keycloakauthenticationprovider in spring context
     * and sets its mapping strategy for roles/authorities (mapping to spring seccurities' default ROLE_... for authorities ).
     *
     * @param auth SecurityBuilder to build authentications and add details like authproviders etc.
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        var keyCloakAuthProvider = keycloakAuthenticationProvider();
        var mapper = new SimpleAuthorityMapper();
        mapper.setPrefix("");
        keyCloakAuthProvider.setGrantedAuthoritiesMapper(mapper);

        auth.authenticationProvider(keyCloakAuthProvider);
    }

    /**
     * define the session auth strategy so that no session is created
     *
     * @return concrete implementation of session authentication strategy
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
            KeycloakAuthenticationProcessingFilter filter) {
        var registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
            KeycloakPreAuthActionsFilter filter) {
        var registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    /**
     * Allows to inject requests scoped wrapper for {@link KeycloakSecurityContext}.
     * <p>
     * Returns the {@link KeycloakSecurityContext} from the Spring
     * {@link ServletRequestAttributes}'s {@link Principal}.
     * <p>
     * The principal must support retrieval of the KeycloakSecurityContext, so at
     * this point, only {@link KeycloakPrincipal} values and
     * {@link KeycloakAuthenticationToken} are supported.
     *
     * @return the current <code>KeycloakSecurityContext</code>
     */
    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public KeycloakSecurityContext provideKeycloakSecurityContext() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Principal principal = attributes.getRequest().getUserPrincipal();
        if(principal == null) {
            return null;
        }

        if(principal instanceof KeycloakAuthenticationToken) {
            principal = (Principal) ((KeycloakAuthenticationToken) principal).getPrincipal();
        }

        if(principal instanceof KeycloakPrincipal) {
            return ((KeycloakPrincipal) principal).getKeycloakSecurityContext();
        }

        return null;
    }
}
