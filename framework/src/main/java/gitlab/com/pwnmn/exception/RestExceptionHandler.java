package gitlab.com.pwnmn.exception;

import brave.Tracer;
import gitlab.com.pwnmn.exception.api.ApiError;
import gitlab.com.pwnmn.exception.api.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static gitlab.com.pwnmn.exception.constants.ErrorCode.*;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Tracer tracer;

    public RestExceptionHandler(Tracer tracer) {
        this.tracer = tracer;
    }

    /**
     * Handles all exceptions not explicitly handled below.
     *
     * @param e Caught exception
     * @return Response entity with custom API error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generalError(final Exception e) {
        log.error("Unhandled exception occured", e);
        return new ResponseEntity<ApiError>(
                new ApiError(
                        API_UNKNOWN_ERROR,
                        Arrays.asList(
                                new ErrorDetail(e.getMessage())
                        ),
                        tracer.currentSpan().context().traceIdString()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

//    /**
//     * Handles exceptions relating to the API.
//     *
//     * @param e Caught exception
//     * @return Response entity with custom API error
//     */
//    @ExceptionHandler(APIException.class)
//    public ResponseEntity<ApiError> apiError(final APIException e) {
//        log.error("API exception occured", e);
//
//        HttpStatus responseStatus;
//        switch (e.getCode()) {
//            case API_UNKNOWN_ERROR:
//                responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//                break;
//            case API_REQUEST_INVALID:
//                responseStatus = HttpStatus.BAD_REQUEST;
//                break;
//            case API_IAM_SERVICE_UNAVAILABLE:
//                responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
//                break;
//            default:
//                responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//                break;
//        }
//
//        log.debug("Http response status is {}", responseStatus.value());
//        return new ResponseEntity<>(
//                new ApiError(
//                        e.getCode(),
//                        e.getDetailErrors(),
//                        tracer.currentSpan().context().traceIdString()
//                ),
//                responseStatus
//        );
//    }

//    /**
//     * Handles exceptions relating to business errors. Business errors are
//     * caused by syntactically (form) correct but semantically (meaning) incorrect requests.
//     *
//     * @param e Caught exception
//     * @return Response entity with custom API error
//     */
//    @ExceptionHandler(BUException.class)
//    public ResponseEntity<ApiError> businessError(final BUException e) {
//        log.error("Business exception occured", e);
//
//        HttpStatus responseStatus;
//        switch (e.getCode()) {
//            case BU_RESOURCE_CANNOT_MODIFY:
//                responseStatus = HttpStatus.UNPROCESSABLE_ENTITY;
//                break;
//            case BU_RESOURCE_NOT_FOUND:
//                responseStatus = HttpStatus.NOT_FOUND;
//                break;
//            case BU_RESOURCE_DUPLICATE_INFO:
//                responseStatus = HttpStatus.UNPROCESSABLE_ENTITY;
//                break;
//            case BU_DEVICE_MEASUREMENT_NOT_FOUND:
//                responseStatus = HttpStatus.UNPROCESSABLE_ENTITY;
//                break;
//            case BU_CONFLICT:
//                responseStatus = HttpStatus.UNPROCESSABLE_ENTITY;
//                break;
//            case DCM_TIMEOUT:
//                responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//                break;
//            default:
//                responseStatus = HttpStatus.UNPROCESSABLE_ENTITY;
//                break;
//        }
//        log.debug("Http response status is {}", responseStatus.value());
//        return new ResponseEntity<>(
//                new ApiError(
//                        e.getCode(),
//                        e.getDetailErrors(),
//                        tracer.currentSpan().context().traceIdString()
//                ),
//                responseStatus
//        );
//    }
//
//    /**
//     * Handles exception relating to authorization.
//     *
//     * @param e Caught exception
//     * @return Response entity with custom API error
//     */
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiError> accessDeniedException(final AccessDeniedException e) {
//        log.error("AccessDenied exception occured", e);
//
//        var errorDetail = new ErrorDetail(
//                e.getMessage()
//        );
//        return new ResponseEntity<>(
//                new ApiError(
//                        IAM_NOT_ALLOWED,
//                        Arrays.asList(
//                                errorDetail
//                        ),
//                        tracer.currentSpan().context().traceIdString()
//                ),
//                HttpStatus.FORBIDDEN
//        );
//    }
//
//    /**
//     * Handles exceptions relating to database access.
//     *
//     * @param e Caught exception
//     * @return Response entity with custom API error
//     */
//    @ExceptionHandler(DBException.class)
//    public ResponseEntity<ApiError> dbError(final DBException e) {
//        log.error("Database exception occured", e);
//        return new ResponseEntity<>(
//                new ApiError(
//                        e.getCode(),
//                        e.getDetailErrors(),
//                        tracer.currentSpan().context().traceIdString()
//                ),
//                HttpStatus.INTERNAL_SERVER_ERROR
//        );
//    }

//    /**
//     * Handles exceptions relating to iam (keycloak) errors.
//     *
//     * @param e Caught exception
//     * @return Response entity with custom API error
//     */
//    @ExceptionHandler(IAMException.class)
//    public ResponseEntity<ApiError> iamError(final IAMException e) {
//        log.error("IAM exception occured", e);
//
//        HttpStatus responseStatus;
//        switch (e.getCode()) {
//            case IAM_CLAIMS_MISSING:
//                responseStatus = HttpStatus.FORBIDDEN;
//                break;
//            case IAM_NOT_ALLOWED:
//                responseStatus = HttpStatus.FORBIDDEN;
//                break;
//            case IAM_UNAUTHORIZED:
//                responseStatus = HttpStatus.UNAUTHORIZED;
//                break;
//            case IAM_TOKEN_INVALID:
//                responseStatus = HttpStatus.UNAUTHORIZED;
//                break;
//            default:
//                responseStatus = HttpStatus.UNAUTHORIZED;
//                break;
//        }
//        log.debug("Http response status is {}", responseStatus.value());
//        return new ResponseEntity<>(
//                new ApiError(
//                        e.getCode(),
//                        e.getDetailErrors(),
//                        tracer.currentSpan().context().traceIdString()
//                ),
//                responseStatus
//        );
//    }

//    /**
//     * Handles exceptions relating to queueing.
//     *
//     * @param e Caught exception
//     * @return Response entity with custom API error
//     */
//    @ExceptionHandler(MQException.class)
//    public ResponseEntity<ApiError> mqException(final MQException e) {
//        log.error("MQ exception occured", e);
//        return new ResponseEntity<>(
//                new ApiError(
//                        e.getCode(),
//                        e.getDetailErrors(),
//                        tracer.currentSpan().context().traceIdString()
//                ),
//                HttpStatus.INTERNAL_SERVER_ERROR
//        );
//    }
//
//    /**
//     * Handles exceptions relating to microservices failure.
//     *
//     * @param e Caught exception
//     * @return Response entity with custom API error
//     */
//    @ExceptionHandler(MSException.class)
//    public ResponseEntity<ApiError> microserviceException(final MSException e) {
//        log.error("Microservice exception occured", e);
//        return new ResponseEntity<>(
//                new ApiError(
//                        e.getCode(),
//                        e.getDetailErrors(),
//                        tracer.currentSpan().context().traceIdString()
//                ),
//                HttpStatus.INTERNAL_SERVER_ERROR
//        );
//    }

    /**
     * Handles exceptions relating to microservices failure.
     *
     * @param e Caught exception
     * @return Response entity with custom API error
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> microserviceException(final ConstraintViolationException e) {
        final List<ErrorDetail> validationErrorDetails = new ArrayList<>();
        e.getConstraintViolations().forEach(
                violation -> {
                    var error = new ErrorDetail(
                            violation.getPropertyPath().toString() + ": " + violation.getInvalidValue(),
                            violation.getMessage()
                    );
                    validationErrorDetails.add(error);
                }
        );
        return new ResponseEntity(
                new ApiError(
                        API_REQUEST_INVALID,
                        validationErrorDetails,
                        tracer.currentSpan().context().traceIdString()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handles all validation exceptions and returns detailed error descriptions.
     *
     * @param ex      Caught validation exception
     * @param headers Provided headers
     * @param status  Corresponding http status
     * @param request Corresponding request
     * @return Response entity with custom API error
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        final List<ErrorDetail> validationErrorDetails = new ArrayList<>();
        fieldErrors.stream().forEach(fieldError -> {
            var error = new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage());
            validationErrorDetails.add(error);
        });
        var apiValidationError = new ApiError(
                API_REQUEST_INVALID,
                validationErrorDetails,
                tracer.currentSpan().context().traceIdString()
        );

        return new ResponseEntity(apiValidationError, headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var supportedMethodBuilder = new StringBuilder();
        supportedMethodBuilder.append("Allowed methods: ");

        var supportedMethods = ex.getSupportedMethods();
        if(supportedMethods.length != 0) {
            supportedMethodBuilder.append(
                    Arrays.stream(supportedMethods)
                            .sorted(
                                    Comparator.naturalOrder()
                            ).collect(
                            Collectors.joining(", ")
                    )
            );
        } else {
            supportedMethodBuilder.append("none");
        }
        var errorDetail = new ErrorDetail(ex.getMethod(), supportedMethodBuilder.toString());
        var methodNotSupportedError = new ApiError(
                API_HTTP_METHOD_NOT_ALLOWED,
                Arrays.asList(errorDetail),
                tracer.currentSpan().context().traceIdString()
        );

        return new ResponseEntity(methodNotSupportedError, headers, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var unsupportedMediaType = new ErrorDetail("media-type", ex.getContentType().toString());
        var contentTypeNotSupportedError
                = new ApiError(
                API_MEDIA_TYPE_NOT_SUPPORTED,
                Arrays.asList(unsupportedMediaType),
                tracer.currentSpan().context().traceIdString()
        );

        return new ResponseEntity(contentTypeNotSupportedError, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errorDetail = new ErrorDetail("path-variable", ex.getVariableName());
        var missingPathVariableError = new ApiError(
                API_MISSING_PATH_VARIABLE,
                Arrays.asList(errorDetail),
                tracer.currentSpan().context().traceIdString()
        );

        return new ResponseEntity(missingPathVariableError, headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errorDetail = new ErrorDetail("JSON request body invalid", ex.getMessage());
        var invalidRequestBody = new ApiError(
                API_REQUEST_INVALID,
                Arrays.asList(errorDetail),
                tracer.currentSpan().context().traceIdString()
        );

        return new ResponseEntity(invalidRequestBody, headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<ErrorDetail> validationErrorDetails = new ArrayList<>();

        ErrorDetail error;
        if(ex instanceof MethodArgumentTypeMismatchException) {
            var methodArgumentEx = (MethodArgumentTypeMismatchException) ex;
            error = new ErrorDetail(methodArgumentEx.getName() + ": " + methodArgumentEx.getValue().toString(), methodArgumentEx.getMessage());
        } else {
            error = new ErrorDetail(ex.getPropertyName() + ": " + ex.getValue().toString(), "Must be of type " + ex.getRequiredType().toString());
        }
        validationErrorDetails.add(error);
        var apiValidationError = new ApiError(
                API_REQUEST_INVALID,
                validationErrorDetails,
                tracer.currentSpan().context().traceIdString()
        );

        return new ResponseEntity(apiValidationError, headers, HttpStatus.BAD_REQUEST);
    }
}
