package com.demo.exception.handler;

import com.demo.exception.FeignResponseException;
import com.demo.exception.model.ApiError;
import com.demo.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        log.error(ExceptionUtils.getMessage(ex));
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError errorModel = new ApiError(httpStatus.value(), "An error occurred");
        return new ResponseEntity<>(errorModel, header(), httpStatus);
    }

    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<ApiError> handleBaseException(ApplicationException ex, WebRequest request) {
        log.error(ExceptionUtils.getMessage(ex));
        ApiError errorModel = new ApiError(ex.getHttpStatus().value(), ex.getMessages());
        return new ResponseEntity<>(errorModel, ex.getHttpStatus());

//        handle multi language exception
//        BaseCustomException customEx = (BaseCustomException) ex;
//        httpStatus = customEx.getHttpStatus();
//        if(customEx.getMessages() != null){
//            errorModel = new ErrorModel(httpStatus.value(), customEx.getMessages(), customEx.getErrorCode());
//        } else if(!CollectionUtils.isEmpty(customEx.getError())){
//            List<String> errorMessages = customEx.getError().entrySet().stream()
//                    .map(e -> {
//                        String message = StringUtils.hasText(e.getValue()) ? e.getValue()
//                                : messageSource.getMessage(e.getKey().getCode(), null, new Locale(""));
//                        return String.format("%s:%s", e.getKey().getCode(), message);
//                    })
//                    .collect(Collectors.toList());
//            String errorCode = StringUtils.hasText(customEx.getErrorCode()) ? customEx.getErrorCode()
//                    : customEx.getError().entrySet().stream().findFirst().get().getKey().getCode();
//            errorModel = new ErrorModel(httpStatus.value(), errorMessages, errorCode);
//        } else {
//            errorModel = new ErrorModel(httpStatus.value(),"null", customEx.getErrorCode());
//        }

    }

    @ExceptionHandler({FeignResponseException.class})
    public ResponseEntity<Object> handleFeignException(Exception ex, WebRequest request) {
        FeignResponseException customEx = (FeignResponseException) ex;
        ApiError errorModel = customEx.getErrorModel();
        HttpStatus httpStatus = customEx.getHttpStatus();
        return new ResponseEntity<>(errorModel, header(), httpStatus);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDenied(Exception ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ApiError errorModel = new ApiError(httpStatus.value(), "Access Denied");
        return new ResponseEntity<>(errorModel, header(), httpStatus);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthentication(Exception ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiError errorModel = new ApiError(httpStatus.value(), "Unauthorized");
        return new ResponseEntity<>(errorModel, header(), httpStatus);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<Object> handleMaxUploadSizeException(Exception ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiError errorModel = new ApiError(
                httpStatus.value(),
                "Kích thước file tối đa là: " + ((MaxUploadSizeExceededException) ex).getMaxUploadSize()
        );
        return new ResponseEntity<>(errorModel, header(), httpStatus);
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<Object> handleBindException(Exception ex, WebRequest request) {
        BindException customEx = (BindException) ex;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<String> messages = customEx.getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.toList());
        ApiError errorModel = new ApiError(httpStatus.value(), messages);
        return new ResponseEntity<>(errorModel, header(), httpStatus);
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleHttpMessageNotReadableException(Exception ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiError errorModel = new ApiError(
                httpStatus.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorModel, header(), httpStatus);
    }

    private HttpHeaders header(){
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return customHeader;
    }

}
