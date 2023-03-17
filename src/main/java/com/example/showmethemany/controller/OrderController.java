package com.example.showmethemany.controller;

import com.example.showmethemany.Service.OrderService;
import com.example.showmethemany.dto.RequestDto.OrderRequestDto;
import com.example.showmethemany.util.globalResponse.GlobalResponseDto;
import com.example.showmethemany.util.globalResponse.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.showmethemany.util.globalResponse.code.StatusCode.OK;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping(value = "/order/{memberId}")
    public ResponseEntity<GlobalResponseDto> upload(@PathVariable Long memberId) {
        orderService.orderProduct(memberId);
        return ResponseUtil.response(OK);
    }

    @DeleteMapping(value = "/order/{memberId}")
    public ResponseEntity<GlobalResponseDto> delete(@PathVariable Long memberId,
                                                    @RequestBody OrderRequestDto orderRequestDto) {
        orderService.deleteOrder(memberId, orderRequestDto);
        return ResponseUtil.response(OK);
    }
}
