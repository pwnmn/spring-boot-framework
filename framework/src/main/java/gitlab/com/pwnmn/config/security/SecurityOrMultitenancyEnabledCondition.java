package gitlab.com.pwnmn.config.security;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ConfigurationCondition;

public class SecurityOrMultitenancyEnabledCondition extends AllNestedConditions {
    public SecurityOrMultitenancyEnabledCondition() {
        super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(name = "framework.security.enabled", havingValue = "true", matchIfMissing = true)
    static class securityEnabled {

    }

    @ConditionalOnProperty(name = "framework.multitenancy.enabled", havingValue = "true", matchIfMissing = true)
    static class multitenancyEnabled {

    }
}
