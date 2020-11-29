package gitlab.com.pwnmn.config;

import lombok.Data;
import org.springframework.util.MultiValueMap;

/**
 *
 */
@Data
public class FeignLogRepresentation {

    private String targetServer;
    private int targetServerPort;
    private String queryParameters;
    private String path;
    private String url;
    private String method;
    private MultiValueMap<String, String> requestHeaders;
    private MultiValueMap<String, String> responseHeaders;
    private String requestPayload;
    private String responsePayload;
    private int processingTime;
    private int responseCode;
    private String statusCode;
}
