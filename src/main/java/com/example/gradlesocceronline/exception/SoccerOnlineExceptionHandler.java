package com.example.gradlesocceronline.exception;

import com.example.gradlesocceronline.model.response.CommonResponse;
import com.example.gradlesocceronline.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class SoccerOnlineExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> exceptionHandling(
            Exception ex, WebRequest request) {
        log.error("internal error:", ex);
        CommonResponse response = CommonResponse.builder()
                .code(Constants.CODE.ERROR.INTERNAL_ERROR)
                .message(Constants.MESSAGE.INTERNAL_ERROR)
                .build();

        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.OK, request);
    }
}
