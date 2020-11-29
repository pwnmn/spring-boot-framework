package gitlab.com.pwnmn.exception.internal;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import gitlab.com.pwnmn.exception.api.ErrorDetail;
import lombok.Getter;
import lombok.ToString;
import gitlab.com.pwnmn.exception.constants.ErrorCode;

import java.util.List;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true, value = {"stackTrace", "cause", "suppressed", "localizedMessage"})
@JsonPropertyOrder({"code", "detailErrors"})
public class BaseApiException extends RuntimeException {
    private static final long serialVersionUID = 1l;

    @JsonProperty("code")
    private final ErrorCode code;
    @JsonProperty("detailErrors")
    private final List<ErrorDetail> detailErrors;

    public BaseApiException(ErrorCode errorCode) {
        this(errorCode, null, null);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BaseApiException(@JsonProperty("code")
                                    ErrorCode errorCode,
                            @JsonProperty("detailErrors")
                                    List<ErrorDetail> detailErrors) {
        this(errorCode, detailErrors, null);
    }

    public BaseApiException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, null, cause);
    }

    public BaseApiException(ErrorCode errorCode, List<ErrorDetail> detailErrors, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode;
        this.detailErrors = detailErrors;
    }
}
