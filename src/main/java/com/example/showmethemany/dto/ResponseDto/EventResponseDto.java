package com.example.showmethemany.dto.ResponseDto;

import com.example.showmethemany.domain.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class EventResponseDto {
    private String eventName;           // 이벤트이름
    private Integer discountRate;           // 할인률
    private EventStatus eventStatus;    // 활성화상태
    private LocalDateTime startedAt;
    private LocalDateTime endAt;

}
