package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.EventRepository;
import com.example.showmethemany.domain.Event;
import com.example.showmethemany.domain.EventStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventScheduler {
    private final EventService eventService;
    private final EventRepository eventRepository;

    @Scheduled(cron = "0 0 * * * *") // 매시각 정각 실행
    public void startEvent() {
        List<Event> eventList = eventRepository.findAll();
        LocalDateTime nowTime = LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Event event : eventList) {
            if (!event.getEventStatus().equals(EventStatus.EXPIRED)) {
                LocalDateTime eventStartTime = event.getStartedAt();
                LocalDateTime eventEndTime = event.getEndAt();

                if ((event.getEventStatus().equals(EventStatus.RESERVED)) && (eventStartTime.getHour() == nowTime.getHour())) {
                    eventService.activeEvent(event.getId());
                } else if ((event.getEventStatus().equals(EventStatus.ACTIVATED)) && (eventEndTime.getHour() == nowTime.getHour())) {
                    eventService.expireEvent(event.getId());
                }
            }
        }
    }


}
