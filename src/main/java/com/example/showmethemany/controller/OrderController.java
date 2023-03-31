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

    @PostMapping(value = "/orderNoneLock/{memberId}")
    public ResponseEntity<GlobalResponseDto> orderNoneLock(@PathVariable Long memberId) {
        orderService.orderProductNoneLock(memberId);
        return ResponseUtil.response(OK);
    }

    @PostMapping(value = "/order/{memberId}")
    public ResponseEntity<GlobalResponseDto> order(@PathVariable Long memberId) {
        orderService.orderProduct(memberId);
        return ResponseUtil.response(OK);
    }

    @PostMapping(value = "/orderRedisson/{memberId}")
    public ResponseEntity<GlobalResponseDto> orderRedisson(@PathVariable Long memberId) {
        orderService.orderProductByRedissonLock(memberId);
        return ResponseUtil.response(OK);
    }

    @PostMapping(value = "/orderLock/{memberId}")
    public ResponseEntity<GlobalResponseDto> orderLock(@PathVariable Long memberId) {
        orderService.orderProductLock(memberId);
        return ResponseUtil.response(OK);
    }

    @PostMapping(value = "/orderSynchronized/{memberId}")
    public ResponseEntity<GlobalResponseDto> orderSynchronized(@PathVariable Long memberId) {
        orderService.orderProductSynchronized(memberId);
        return ResponseUtil.response(OK);
    }

    @DeleteMapping(value = "/order/{memberId}")
    public ResponseEntity<GlobalResponseDto> delete(@PathVariable Long memberId,
                                                    @RequestBody OrderRequestDto orderRequestDto) {
        orderService.deleteOrder(memberId, orderRequestDto);
        return ResponseUtil.response(OK);
    }
}
