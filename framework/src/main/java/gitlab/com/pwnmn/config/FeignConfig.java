package gitlab.com.pwnmn.config;

import feign.Logger;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

/**
 * Configuration for feign logging level.
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
        return new FeignFormatterRegistrar() {
            @Override
            public void registerFormatters(FormatterRegistry formatterRegistry) {
                DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
                registrar.setUseIsoFormat(true);
                registrar.registerFormatters(formatterRegistry);
            }
        };
    }

    @Bean
    public Logger getLogger() {
        return new FeignLogger();
    }
}