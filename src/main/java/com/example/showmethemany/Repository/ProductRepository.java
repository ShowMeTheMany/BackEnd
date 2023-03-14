package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.ResponseDto.ProductFilterResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ProductRepository  extends JpaRepository<Products, Long> {
    Optional<Products> findById(Long id);
    Page<ProductFilterResponseDto> mainFilter(Pageable pageable, Long categoryId, Long minPrice, Long maxPrice, String stock, String sort);
}
