package com.example.showmethemany.util.globalResponse;

import com.example.showmethemany.util.globalResponse.code.StatusCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalResponseDto<T> {
    private HttpStatus httpStatus;
    private String statusCode;
    private String statusMsg;
    private T data;

    public GlobalResponseDto(StatusCode statusCode) {
        this.httpStatus = statusCode.getHttpStatus();
        this.statusCode = statusCode.getStatusCode();
        this.statusMsg = statusCode.getStatusMsg();
    }

    public GlobalResponseDto(StatusCode statusCode, T data) {
        this.httpStatus = statusCode.getHttpStatus();
        this.statusCode = statusCode.getStatusCode();
        this.statusMsg = statusCode.getStatusMsg();
        this.data = data;
    }
}
