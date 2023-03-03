package com.example.showmethemany.util.globalResponse.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusCode {

    //TODO ========================= 예외 응답 코드 ===============================
    // 400 BAD_REQUEST : 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "요청이 올바르지 않습니다"),
    BAD_REQUEST_TOKEN(HttpStatus.UNAUTHORIZED, "401", "토큰이 유효하지 않습니다."),


    //TODO ========================= 성공 응답 코드 ===============================

    OK(HttpStatus.OK, "200", "응답이 정상 처리 되었습니다."),

    ;
    private final HttpStatus httpStatus;
    private final String statusCode;
    private final String statusMsg;

    StatusCode(HttpStatus httpStatus, String statusCode, String statusMsg) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }
}
