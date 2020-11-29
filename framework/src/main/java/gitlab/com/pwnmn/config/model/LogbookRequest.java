package gitlab.com.pwnmn.config.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LogbookRequest {

    private Map<String, List<String>> headers;
    private String host;
    private String method;
    private String path;
    private String query;
    private String remote;
    private String requestUri;
    private String body;
    private String contentType;
    private String charSet;
    private String origin;

}
