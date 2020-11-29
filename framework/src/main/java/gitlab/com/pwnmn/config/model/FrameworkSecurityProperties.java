package gitlab.com.pwnmn.config.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@ConfigurationProperties(prefix = "framework.security")

@Getter
@Setter
@AllArgsConstructor
public class FrameworkSecurityProperties {
    // Option to whitelist URLs from the application.yml of importing project
    private List<SecurityWhitelistElement> whitelist = new ArrayList<SecurityWhitelistElement>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SecurityWhitelistElement {
        private String method;
        private String url;
    }
}
