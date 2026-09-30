package com.example.crud.repository;

import com.example.crud.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Cari produk berdasarkan nama (case-insensitive, partial match)
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Cari produk berdasarkan nama dengan pagination
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
