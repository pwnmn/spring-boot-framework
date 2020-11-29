package gitlab.com.pwnmn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static net.logstash.logback.argument.StructuredArguments.fields;
import static net.logstash.logback.argument.StructuredArguments.keyValue;


@Slf4j
public class FeignLogger extends Logger {

    private final ThreadLocal<FeignLogRepresentation> threadLocal = new ThreadLocal<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        FeignLogRepresentation logInfo = new FeignLogRepresentation();

        URI uri = extractURI(request.url());
        if(uri != null) {
            logInfo.setTargetServer(uri.getHost());
            logInfo.setTargetServerPort(uri.getPort());
            logInfo.setQueryParameters(uri.getQuery());
            logInfo.setPath(uri.getPath());
            logInfo.setUrl(uri.toString());
        }

        logInfo.setMethod(request.httpMethod().name());
        MultiValueMap<String, String> headers = extractHeaders(request.headers());
        logInfo.setRequestHeaders(headers);

        var body = request.body();
        var charset = request.charset();
        if(body != null && body.length > 0) {
            logInfo.setRequestPayload(
                    new String(
                            body, charset
                    )
            );
        }

        threadLocal.set(logInfo);
    }


    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
            throws IOException {
        FeignLogRepresentation logInfo = threadLocal.get();
        logInfo.setProcessingTime((int) elapsedTime);
        logInfo.setResponseCode(response.status());
        logInfo.setStatusCode(response.reason());
        logInfo.setResponseHeaders(extractHeaders(response.headers()));

        var body = response.body();
        if(body != null) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            logInfo.setResponsePayload(
                    new String(bodyData, StandardCharsets.UTF_8)
            );
            writeLog(logInfo);
            return response.toBuilder().body(bodyData).build();
        }

        writeLog(logInfo);

        return response;
    }

    private void writeLog(FeignLogRepresentation logInfo) {
        if(nonNull(logInfo)) {
            var path = logInfo.getPath();
            var method = logInfo.getMethod();
            log.debug(
                    "Feign {} {}",
                    method,
                    path,
                    fields(logInfo),
                    keyValue("messageType", "feign")
            );
        }
    }


    @Override
    protected void log(String configKey, String format, Object... args) {

    }

    private MultiValueMap<String, String> extractHeaders(Map<String, Collection<String>> headers) {
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        headers.forEach((key, value) -> {
            if(!"Authorization".equals(key)) {
                result.put(key, (List<String>) value);
            }
        });
        return result;
    }

    private URI extractURI(String url) {
        try {
            return new URI(url);
        } catch(URISyntaxException e) {
            log.debug("Could not extract URL {}", e.getMessage());
        }
        return null;
    }

}