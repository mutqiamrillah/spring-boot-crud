package com.example.crud.controller;

import com.example.crud.dto.ApiResponse;
import com.example.crud.dto.request.ProductRequest;
import com.example.crud.dto.response.ProductResponse;
import com.example.crud.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "API untuk manajemen produk")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /api/products — Ambil semua produk (dengan pagination)
     * Query params: page, size, sort (contoh: ?page=0&size=10&sort=name,asc)
     */
    @GetMapping
    @Operation(summary = "Ambil semua produk", description = "Mendapatkan daftar semua produk dengan pagination dan sorting")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAllProducts(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<ProductResponse> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(ApiResponse.success("Berhasil mengambil semua produk", products));
    }

    /**
     * GET /api/products/{id} — Ambil produk berdasarkan ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Ambil produk by ID", description = "Mendapatkan detail produk berdasarkan ID")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Berhasil mengambil produk", product));
    }

    /**
     * POST /api/products — Tambah produk baru
     */
    @PostMapping
    @Operation(summary = "Tambah produk baru", description = "Membuat produk baru")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody ProductRequest request) {
        ProductResponse created = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Produk berhasil ditambahkan", created));
    }

    /**
     * PUT /api/products/{id} — Update produk
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update produk", description = "Mengupdate data produk berdasarkan ID")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse updated = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success("Produk berhasil diupdate", updated));
    }

    /**
     * DELETE /api/products/{id} — Hapus produk
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Hapus produk", description = "Menghapus produk berdasarkan ID")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Produk berhasil dihapus", null));
    }

    /**
     * GET /api/products/search?name=keyword — Cari produk berdasarkan nama (dengan pagination)
     */
    @GetMapping("/search")
    @Operation(summary = "Cari produk", description = "Mencari produk berdasarkan nama dengan pagination")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> searchProducts(
            @RequestParam String name,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<ProductResponse> products = productService.searchProducts(name, pageable);
        return ResponseEntity.ok(ApiResponse.success("Hasil pencarian produk", products));
    }
}
