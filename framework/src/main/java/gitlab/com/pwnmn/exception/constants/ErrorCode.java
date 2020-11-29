package gitlab.com.pwnmn.exception.constants;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Collection of all possible error codes. These are used together with all custom exceptions
 * extended from BaseApiException.
 */
@Slf4j
@Getter
@ToString
public enum ErrorCode {

    API_UNKNOWN_ERROR("API-1", "Unknown error"),
    API_REQUEST_INVALID("API-2", "Invalid request"),
    API_HTTP_METHOD_NOT_ALLOWED("API-4", "HTTP method not supported"),
    API_MEDIA_TYPE_NOT_SUPPORTED("API-5", "Media type not supported"),
    API_MISSING_PATH_VARIABLE("API-6", "Missing path variable"),
    API_DB_CONSTRAINT_VIOLATION("API-7", "Database constraint violation");

    public static final String MESSAGE_PARAMS_PATTERN = "\\{(.*?)\\}";
    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
