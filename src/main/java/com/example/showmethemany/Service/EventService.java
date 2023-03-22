package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.EventRepository;
import com.example.showmethemany.Repository.ProductRepository;
import com.example.showmethemany.domain.Event;
import com.example.showmethemany.domain.EventStatus;
import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.RequestDto.EventRequestDto;
import com.example.showmethemany.dto.ResponseDto.EventResponseDto;
import com.example.showmethemany.util.globalResponse.CustomException;
import com.example.showmethemany.util.globalResponse.code.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Transactional
    public Event createEvent(EventRequestDto eventRequestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startTime = LocalDateTime.parse(eventRequestDto.getStartedAt(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(eventRequestDto.getEndAt(), formatter);

        List<Products> productsList = productRepository.findAll(); // 테스트 필요

        List<Long> productsIdList = eventRequestDto.getProductList();

        Event event = Event.builder()
                .eventName(eventRequestDto.getEventName())
                .startedAt(startTime)
                .endAt(endTime)
                .eventStatus(eventRequestDto.getEventStatus())
                .discountRate(eventRequestDto.getDiscountRate())
                .build();
        eventRepository.save(event);

//        for (Long Id : productsIdList){
//            Products products = productRepository.findById(Id).orElseThrow(
//                    () -> new CustomException(StatusCode.BAD_REQUEST)
//            );
//            products.updateProductsEventId(event);
//            products.updateProductsDiscount(products.getPrice()*((event.getDiscountRate()/100)));
//        }
        productService.updateProducts(productsIdList, event);
        return event;
    }


    public EventResponseDto getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new CustomException(StatusCode.BAD_REQUEST)
        );
        return EventResponseDto.builder()
                .eventName(event.getEventName())
                .startedAt(event.getStartedAt())
                .endAt(event.getEndAt())
                .discountRate(event.getDiscountRate())
                .eventStatus(event.getEventStatus())
                .build();
    }

    @Transactional
    public EventResponseDto modifyEvent(Long eventId, EventRequestDto eventRequestDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new CustomException(StatusCode.BAD_REQUEST)
        );
        verifyEventField(event, eventRequestDto);
        return responseDtoBuilder(event);
    }

    private void verifyEventField(Event event, EventRequestDto eventRequestDto) {
        if (eventRequestDto.getEventName() != null) {
            event.updateEvent(eventRequestDto.getEventName());
        }
        if (eventRequestDto.getStartedAt() != null) {
            event.updateEvent(eventRequestDto.getStartedAt());
        }
        if (eventRequestDto.getEndAt() != null) {
            event.updateEvent(eventRequestDto.getEndAt());
        }
        if (eventRequestDto.getDiscountRate() != null) {
            event.updateEvent(eventRequestDto.getDiscountRate());
        }
        if (eventRequestDto.getEventStatus() != null) {
            event.updateEvent(eventRequestDto.getEventStatus());
        }
    }

    private EventResponseDto responseDtoBuilder(Event event) {
        return EventResponseDto.builder()
                .eventName(event.getEventName())
                .startedAt(event.getStartedAt())
                .endAt(event.getEndAt())
                .discountRate(event.getDiscountRate())
                .eventStatus(event.getEventStatus())
                .build();
    }

    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new CustomException(StatusCode.BAD_REQUEST)
        );
        eventRepository.deleteById(event.getId());
    }

    @Transactional
    public void activeEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new CustomException(StatusCode.BAD_REQUEST)
        );
        event.updateEvent(EventStatus.ACTIVATED);
    }

    @Transactional
    public void expireEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new CustomException(StatusCode.BAD_REQUEST)
        );
        event.updateEvent(EventStatus.EXPIRED);
    }

}
