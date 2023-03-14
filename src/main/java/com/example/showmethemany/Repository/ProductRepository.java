package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ProductRepository  extends JpaRepository<Products, Long> {

    Optional<Products> findById(Long id);
}
