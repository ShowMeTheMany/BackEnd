package com.example.showmethemany.util.globalResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    // 커스텀한 실행 예외
    @ExceptionHandler(value= {CustomException.class})
    protected ResponseEntity<?> handleCustomException(CustomException e) {
        log.error("====================== handleCustomException에서 처리한 에러 : {}", e.getMessage());
        return ResponseUtil.response(e.getStatusCode());
    }
}