package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
