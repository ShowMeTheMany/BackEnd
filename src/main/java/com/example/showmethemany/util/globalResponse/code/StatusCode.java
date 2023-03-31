package com.example.showmethemany.util.globalResponse.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusCode {

    //TODO ========================= 예외 응답 코드 ===============================
    // 400 BAD_REQUEST : 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "요청이 올바르지 않습니다"),
    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "400", "존재하지 않는 계정입니다."),
    NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "400", "존재하지 않는 상품입니다."),
    NOT_FOUND_BASKET(HttpStatus.BAD_REQUEST, "400", "존재하지 않는 장바구니입니다."),
    NOT_EXIST_ORDER(HttpStatus.BAD_REQUEST, "400", "주문 내역이 비어있습니다."),
    QUANTITY_OF_ORDERS_EXCEEDS_STOCK(HttpStatus.BAD_REQUEST, "400", "주문 수량이 잔여 재고보다 많습니다."),
    BAD_REQUEST_TOKEN(HttpStatus.UNAUTHORIZED, "401", "토큰이 유효하지 않습니다."),
    EMPTY_RESULT_EXCEPTION(HttpStatus.BAD_REQUEST, "400", "조회된 상품이 없습니다."),


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
