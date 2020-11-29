package gitlab.com.pwnmn.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AutoconfigurationExcluder implements ApplicationListener<ApplicationEvent> {

    private static final String FRAMEWORK_SECURITY_ENABLED_PROP = "framework.security.enabled";
    private static final Map<String, Object> exclusionMap = new HashMap<>();

    static {
        exclusionMap.put("spring.autoconfigure.exclude", "org.keycloak.adapters.springboot.KeycloakAutoConfiguration");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ApplicationEnvironmentPreparedEvent) {
            ConfigurableEnvironment environment = ((ApplicationEnvironmentPreparedEvent) event).getEnvironment();

            if(environment.containsProperty(FRAMEWORK_SECURITY_ENABLED_PROP)) {
                var securityEnabled = (boolean) environment.getProperty(FRAMEWORK_SECURITY_ENABLED_PROP, Boolean.class);
                if(!securityEnabled) {
                    environment.getPropertySources().addFirst(
                            new MapPropertySource("excludeKeycloakProperties", exclusionMap)
                    );
                }
            }
        }
    }

}