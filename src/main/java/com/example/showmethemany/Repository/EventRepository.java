package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
