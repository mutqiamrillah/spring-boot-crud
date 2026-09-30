package com.example.crud.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO untuk request create/update produk.
 * Validasi dilakukan di sini, bukan di entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Nama produk tidak boleh kosong")
    @Size(min = 2, max = 100, message = "Nama produk harus antara 2-100 karakter")
    private String name;

    @Size(max = 500, message = "Deskripsi maksimal 500 karakter")
    private String description;

    @NotNull(message = "Harga tidak boleh kosong")
    @Positive(message = "Harga harus lebih dari 0")
    private BigDecimal price;

    @NotNull(message = "Stok tidak boleh kosong")
    private Integer stock;
}
