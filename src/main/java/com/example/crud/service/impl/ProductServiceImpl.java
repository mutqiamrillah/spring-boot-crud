package com.example.crud.service.impl;

import com.example.crud.dto.request.ProductRequest;
import com.example.crud.dto.response.ProductResponse;
import com.example.crud.entity.Product;
import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.mapper.ProductMapper;
import com.example.crud.repository.ProductRepository;
import com.example.crud.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("Mengambil semua produk — page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        log.info("Mengambil produk dengan ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produk dengan ID {} tidak ditemukan", id);
                    return new ResourceNotFoundException("Produk dengan ID " + id + " tidak ditemukan");
                });
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Membuat produk baru: {}", request.getName());
        Product product = productMapper.toEntity(request);
        Product saved = productRepository.save(product);
        log.info("Produk berhasil dibuat dengan ID: {}", saved.getId());
        return productMapper.toResponse(saved);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("Mengupdate produk dengan ID: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produk dengan ID {} tidak ditemukan untuk update", id);
                    return new ResourceNotFoundException("Produk dengan ID " + id + " tidak ditemukan");
                });

        productMapper.updateEntityFromRequest(request, existingProduct);
        Product updated = productRepository.save(existingProduct);
        log.info("Produk dengan ID {} berhasil diupdate", id);
        return productMapper.toResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Menghapus produk dengan ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produk dengan ID {} tidak ditemukan untuk dihapus", id);
                    return new ResourceNotFoundException("Produk dengan ID " + id + " tidak ditemukan");
                });
        productRepository.delete(product);
        log.info("Produk dengan ID {} berhasil dihapus", id);
    }

    @Override
    public Page<ProductResponse> searchProducts(String name, Pageable pageable) {
        log.info("Mencari produk dengan keyword: '{}' — page: {}, size: {}", name, pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(productMapper::toResponse);
    }
}
