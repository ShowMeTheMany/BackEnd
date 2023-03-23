package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.EventRepository;
import com.example.showmethemany.domain.Event;
import com.example.showmethemany.domain.EventStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventSchedulerTest {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;


    @Test
    void startEvent(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 1; i <= 2; i++) {
            int finalI = i;
            executorService.execute(() -> {
                if (finalI == 1){
                    eventService.activeEvent(5L);
                } else if (finalI == 2) {
                    eventService.expireEvent(5L);
                }
            });
        }
        Event event = eventRepository.findById(5L).get();
        assertThat(event.getEventStatus()).isEqualTo(EventStatus.ACTIVATED);
    }
}