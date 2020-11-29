package gitlab.com.pwnmn.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    /**
     *
     * By default Spring will map roles in the JWT token to 'ROLE_${claim}'.
     * To avoid this, we remove the spring default role prefix "ROLE_" in front of every role.
     * Now the actual roles can be used with the @Secured annotation.
     *
     * @return
     */
    protected AccessDecisionManager accessDecisionManager() {
        AffirmativeBased accessDecisionManager = (AffirmativeBased) super.accessDecisionManager();

        //Remove the ROLE_ prefix from RoleVoter for @Secured and hasRole checks on methods
        accessDecisionManager.getDecisionVoters().stream()
                .filter(RoleVoter.class::isInstance)
                .map(RoleVoter.class::cast)
                .forEach(it -> it.setRolePrefix(""));

        return accessDecisionManager;
    }

    /**
     * Remove spring default "ROLE_" prefix when mapping roles.
     */
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
