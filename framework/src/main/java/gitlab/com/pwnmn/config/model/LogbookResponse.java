package gitlab.com.pwnmn.config.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LogbookResponse {

    private Map<String, List<String>> headers;
    private String host;
    private String body;
    private String contentType;
    private String charSet;
    private int status;
    private String reasonPhrase;
    private String origin;
    private String requestMethod;
    private String requestUri;
}

