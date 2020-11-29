package com.github.pwnmn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Springfox configuration which exposes the swagger definition of the generated server.
 */
@Configuration
public class SpringfoxConfig {

    private final GitProperties gitProperties;

    public SpringfoxConfig(@Autowired(required = false) GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Bean
    public Docket internalApi() {
        Docket thisDocket;
        thisDocket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("OpenApi Codegen")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.pwnmn.server"))
                .paths(PathSelectors.any())
                .build()
                .forCodeGeneration(true);

        return thisDocket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Petstore example API")
                .contact(new Contact("Peter Weismann", "https://github.com/pwnmn", ""))
                .description(gitInfo())
                .version("1.0")
                .build();
    }

    private String gitInfo() {
        if(gitProperties != null) {
            String branch = gitProperties.getBranch();
            String commitId = gitProperties.getShortCommitId();
            ZoneId zoneId = ZoneId.of("UTC");
            var dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy | HH:mm:ss z");
            String commitTime = gitProperties.getCommitTime().atZone(zoneId).format(dateTimeFormatter);
            String gitInfo = constructGitInfo(commitId, branch, commitTime);
            return gitInfo;
        } else {
            return "No commit information available";
        }
    }

    private String constructGitInfo(String commitId, String branch, String commitTime) {
        var builder = new StringBuilder();
        builder.append("Commit: " + commitId + "\n");
        builder.append("Message: " + gitProperties.get("commit.message.short") + "\n");
        builder.append("Branch: " + branch + "\n");
        builder.append("Time: " + commitTime + "\n");
        return builder.toString();
    }
}
