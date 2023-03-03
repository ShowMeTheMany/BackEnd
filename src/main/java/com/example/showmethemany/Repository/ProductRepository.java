package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
