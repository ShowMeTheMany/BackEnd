package com.example.showmethemany.dto.RequestDto;

import com.example.showmethemany.domain.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequestDto {
    private String eventName;           // 이벤트이름
    private Integer discountRate;       // 할인률
    private EventStatus eventStatus;    // 활성화상태
    private String startedAt;             // 시작시간
    private String endAt;               // 마감시간
    private List<Long> productList;
}
