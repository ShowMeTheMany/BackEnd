package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products, Long> {
    Optional<Products> findById(Long productId);
}
