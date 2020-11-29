package gitlab.com.pwnmn.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Disables security completely when framework.security.enabled is set to false. Not activated
 * by default.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "framework.security.enabled", havingValue = "false", matchIfMissing = false)
class NoSecurityConfig extends WebSecurityConfigurerAdapter {


    public NoSecurityConfig() {
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        var ignoredPaths = web.ignoring();
        ignoredPaths.antMatchers("/**");
    }
}
