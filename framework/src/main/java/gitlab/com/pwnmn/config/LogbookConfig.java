package gitlab.com.pwnmn.config;

import gitlab.com.pwnmn.config.model.LogbookRequest;
import gitlab.com.pwnmn.config.model.LogbookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zalando.logbook.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static net.logstash.logback.argument.StructuredArguments.fields;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
@Component
@ConditionalOnProperty(name = "framework.logbook.enabled", havingValue = "true", matchIfMissing = true)
public class LogbookConfig {

    @Bean
    public Sink sink() {
        return new Sink() {
            @Override
            public void write(Precorrelation precorrelation, HttpRequest request) throws IOException {
                LogbookRequest logbookRequest = new LogbookRequest();
                var body = new String(request.getBody(), StandardCharsets.UTF_8);
                logbookRequest.setBody(body);
                logbookRequest.setCharSet(request.getCharset().toString());
                logbookRequest.setContentType(request.getContentType());
                logbookRequest.setHeaders(request.getHeaders());
                logbookRequest.setHost(request.getHost());
                var method = request.getMethod();
                logbookRequest.setMethod(method);
                var path = request.getPath();
                logbookRequest.setPath(path);
                logbookRequest.setQuery(request.getQuery());
                logbookRequest.setRemote(request.getRemote());
                logbookRequest.setRequestUri(request.getRequestUri());
                var origin = request.getOrigin();

                if(origin != null) {
                    logbookRequest.setOrigin(origin.name());
                }
                log.info("Request {} {}", method, path, fields(logbookRequest), keyValue("messageType", "request"));
            }

            @Override
            public void write(Correlation correlation, HttpRequest request, HttpResponse response) throws IOException {
                LogbookResponse logbookResponse = new LogbookResponse();
                HttpResponse responseWithBody = response.withBody();

                logbookResponse.setBody(responseWithBody.getBodyAsString());
                logbookResponse.setCharSet(responseWithBody.getCharset().toString());
                logbookResponse.setContentType(responseWithBody.getContentType());
                logbookResponse.setHeaders(responseWithBody.getHeaders());
                logbookResponse.setStatus(responseWithBody.getStatus());
                logbookResponse.setReasonPhrase(responseWithBody.getReasonPhrase());
                logbookResponse.setRequestUri(request.getRequestUri());
                var method = request.getMethod();
                logbookResponse.setRequestMethod(method);
                var origin = response.getOrigin();

                if(origin != null) {
                    logbookResponse.setOrigin(origin.name());
                }

                var path = request.getPath();
                log.info("Response {} {}", method, path, fields(logbookResponse), keyValue("messageType", "response"));
            }
        };
    }
}