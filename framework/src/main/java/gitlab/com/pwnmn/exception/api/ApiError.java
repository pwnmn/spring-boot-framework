package gitlab.com.pwnmn.exception.api;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import gitlab.com.pwnmn.exception.constants.ErrorCode;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@ApiModel("Standard error response")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@JsonPropertyOrder({"code", "message", "errorDetails", "trace", "timestamp"})
public class ApiError implements Serializable {

    private final String code;
    private final String message;
    private final List<ErrorDetail> errorDetails;
    private final Instant timestamp;
    private final String traceId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ApiError(@JsonProperty("code") ErrorCode code, @JsonProperty("errorDetails") List<ErrorDetail> errors) {
        super();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.errorDetails = errors;
        this.timestamp = Instant.now();
        this.traceId = null;
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ApiError(ErrorCode code, List<ErrorDetail> errors, String traceId) {
        super();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.errorDetails = errors;
        this.timestamp = Instant.now();
        this.traceId = traceId;
    }

    public ApiError(ErrorCode code) {
        this(code, null);
    }

    @JsonProperty(value = "code")
    @ApiModelProperty(example = "API-0001", value = "Standard error code")
    public String getCode() {
        return code;
    }

    @JsonProperty(value = "message")
    @ApiModelProperty(example = "Unknown error has occurred", value = "Descriptive error messages")
    public String getMessage() {
        return message;
    }

    @JsonProperty(value = "errorDetails")
    @ApiModelProperty(value = "In case multiple errors caused the ApiError to be thrown. For example if multiple validations were violated. \n" +
            "Can be used to provide further information about the error.")
    public List<ErrorDetail> getErrors() {
        return errorDetails;
    }

    @JsonProperty(value = "timestamp")
    @ApiModelProperty(value = "Timestamp that server has response in UTC format with assumption of no offset", example = "2020-05-19T06:33:58Z")
    public Instant getTimestamp() {
        return timestamp;
    }

    @JsonProperty(value = "trace")
    @ApiModelProperty(value = "Unique ID to identify HTTP request")
    public String getTraceId() {
        return traceId;
    }

}
