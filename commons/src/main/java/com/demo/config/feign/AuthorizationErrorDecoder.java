package com.demo.config.feign;

import com.demo.enums.ErrorMessage;
import com.demo.exception.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorizationErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new Default();

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 401) {
            log.error(ErrorMessage.UNAUTHORIZED);
            return new UnauthorizedException();
        }
        return errorDecoder.decode(methodKey, response);
    }
}