package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Products> findByProductId (Long productId);
}
