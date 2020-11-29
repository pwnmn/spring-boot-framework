package gitlab.com.pwnmn.config.security;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import gitlab.com.pwnmn.config.TenantContext;
import gitlab.com.pwnmn.util.JwtUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Resolves tenant information according to provided JWT token. Custom claim 'tenant' is used to
 * identify the tenant.
 *
 */
@Slf4j
@Configuration
@Conditional(SecurityOrMultitenancyEnabledCondition.class)
public class MultitenantConfigResolver implements KeycloakConfigResolver {

    private static final String DEFAULT_REALM = "master";
    private static final String tenant = "tenant";

    private final Map<String, KeycloakDeployment> cache = new ConcurrentHashMap<String, KeycloakDeployment>();
    private final AdapterConfig adapterConfig;
    private final JwtUtil jwtUtil;

    public MultitenantConfigResolver(AdapterConfig adapterConfig, JwtUtil jwtUtil) {
        this.adapterConfig = adapterConfig;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
        Optional<String> jwtRealm = jwtUtil.extractClaimFromToken(
                request.getHeader(HttpHeaders.AUTHORIZATION), tenant
        );

        KeycloakDeployment deployment;
        if (jwtRealm.isPresent()) {
            deployment = retrieveOrCreateDeployment(jwtRealm.get());
            TenantContext.setCurrentTenant(deployment.getRealm());
        } else {
            deployment = retrieveOrCreateDeployment(DEFAULT_REALM);
            log.error("Realm could not be extracted from JWT token");
        }
        log.trace("Deployed realm: {}", deployment.getRealm());
        return deployment;
    }

    /**
     * Retrieve cached deployment or insert into cache if unknown.
     *
     * @param realm the current realm
     * @return this realms keycloak deployment
     */
    private KeycloakDeployment retrieveOrCreateDeployment(String realm) {
        KeycloakDeployment deployment = cache.get(realm);
        if(deployment == null) {
            deployment = createKeycloakDeployment(realm);
            cache.put(realm, deployment);
        }
        return deployment;
    }

    private KeycloakDeployment createKeycloakDeployment(String realm) {
        adapterConfig.setRealm(realm);
        return KeycloakDeploymentBuilder.build(adapterConfig);
    }
}

