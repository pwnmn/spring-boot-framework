package gitlab.com.pwnmn.config.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "framework.cors")

@Getter
@Setter
public class CorsProperties {

    // Domains to be activated for CORS
    private List<String> domains = new ArrayList<String>();

}
