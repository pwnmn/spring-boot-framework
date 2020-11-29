package gitlab.com.pwnmn.exception.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@JsonPropertyOrder({"cause", "message"})
public class ErrorDetail implements Serializable {

    private final String cause;
    private String message;

    public ErrorDetail(String cause) {
        this.cause = cause;
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ErrorDetail(@JsonProperty("cause") String cause, @JsonProperty("message") String message) {
        this.cause = cause;
        this.message = message;
    }

    @ApiModelProperty(example = "name", value = "Error cause")
    public String getCause() {
        return cause;
    }

    @ApiModelProperty(example = "must not be null", value = "Detail error description")
    public String getMessage() {
        return message;
    }
}
