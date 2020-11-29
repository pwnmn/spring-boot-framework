package gitlab.com.pwnmn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import gitlab.com.pwnmn.interceptor.ExecutionTimeInterceptor;

/**
 * Registers interceptors.
 */
@EnableConfigurationProperties
@Configuration
public class FrameworkConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        var execTimeLoad = Boolean.valueOf(
                env.getProperty("framework.interceptors.executiontime", "false")
        );
        if(execTimeLoad) {
            registry.addInterceptor(new ExecutionTimeInterceptor());
        }
    }
}
