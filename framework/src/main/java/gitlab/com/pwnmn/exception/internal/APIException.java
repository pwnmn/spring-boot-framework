package gitlab.com.pwnmn.exception.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gitlab.com.pwnmn.exception.api.ErrorDetail;
import lombok.Builder;
import lombok.Getter;
import gitlab.com.pwnmn.exception.constants.ErrorCode;

import java.util.Arrays;
import java.util.List;

/**
 * Can be thrown in case of all kinds of errors relating to the API. These can be looked up
 * with the ErrorCode enum. These start with the prefix 'API_'
 */
@Getter
public class APIException extends BaseApiException {

    public APIException(ErrorCode errorCode) {
        super(errorCode);
    }

    public APIException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public APIException(@JsonProperty("code") ErrorCode errorCode,
                        @JsonProperty("detailErrors") List<ErrorDetail> detailErrors) {
        super(errorCode, detailErrors);
    }

    public APIException(ErrorCode errorCode, List<ErrorDetail> detailErrors, Throwable cause) {
        super(errorCode, detailErrors, cause);
    }

    @Builder
    public APIException(ErrorCode code, String detailCode, String detailMessage) {
        super(code, Arrays.asList(new ErrorDetail(detailCode, detailMessage)));
    }
}
