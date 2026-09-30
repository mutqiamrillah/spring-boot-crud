# 🚀 Spring Boot CRUD - Product Management API

Project CRUD sederhana menggunakan **Java Spring Boot 3.3** untuk manajemen produk.

## Tech Stack

| Teknologi | Keterangan |
|-----------|------------|
| Java 17+ | Bahasa pemrograman |
| Spring Boot 3.3.2 | Framework utama |
| Spring Data JPA | ORM & database access |
| H2 Database | In-memory database (untuk development) |
| Lombok | Mengurangi boilerplate code |
| Jakarta Validation | Validasi input |

## 📁 Struktur Project

```
spring-boot-crud/
├── pom.xml
└── src/main/
    ├── java/com/example/crud/
    │   ├── SpringBootCrudApplication.java    # Entry point
    │   ├── controller/
    │   │   └── ProductController.java        # REST endpoints
    │   ├── dto/
    │   │   └── ApiResponse.java              # Response wrapper
    │   ├── entity/
    │   │   └── Product.java                  # JPA entity
    │   ├── exception/
    │   │   ├── GlobalExceptionHandler.java   # Error handling
    │   │   └── ResourceNotFoundException.java
    │   ├── repository/
    │   │   └── ProductRepository.java        # Data access
    │   └── service/
    │       ├── ProductService.java           # Interface
    │       └── impl/
    │           └── ProductServiceImpl.java   # Implementasi
    └── resources/
        ├── application.properties            # Konfigurasi
        └── data.sql                          # Sample data
```

## 🛠️ Cara Menjalankan

### Prasyarat
- **Java 17** atau lebih baru (`java -version`)
- **Maven** (`mvn -version`) atau gunakan Maven wrapper

### Jalankan Aplikasi

```bash
cd spring-boot-crud

# Menggunakan Maven
mvn spring-boot:run

# ATAU menggunakan Maven wrapper (jika tersedia)
./mvnw spring-boot:run
```

Aplikasi akan berjalan di `http://localhost:8080`

## 📋 API Endpoints

### 1. ✅ Ambil Semua Produk
```bash
curl http://localhost:8080/api/products
```

### 2. 🔍 Ambil Produk by ID
```bash
curl http://localhost:8080/api/products/1
```

### 3. ➕ Tambah Produk Baru
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Airpods Pro 2",
    "description": "TWS Apple dengan ANC dan USB-C",
    "price": 3800000,
    "stock": 20
  }'
```

### 4. ✏️ Update Produk
```bash
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop ASUS ROG (Updated)",
    "description": "Laptop gaming dengan RTX 4070",
    "price": 22000000,
    "stock": 8
  }'
```

### 5. 🗑️ Hapus Produk
```bash
curl -X DELETE http://localhost:8080/api/products/3
```

### 6. 🔎 Cari Produk berdasarkan Nama
```bash
curl "http://localhost:8080/api/products/search?name=laptop"
```

## 📦 Contoh Response

### Success Response
```json
{
  "status": 200,
  "message": "Berhasil mengambil semua produk",
  "data": [
    {
      "id": 1,
      "name": "Laptop ASUS ROG",
      "description": "Laptop gaming dengan RTX 4060",
      "price": 18500000.00,
      "stock": 10,
      "createdAt": "2026-07-19T21:25:00",
      "updatedAt": "2026-07-19T21:25:00"
    }
  ],
  "timestamp": "2026-07-19T21:25:00"
}
```

### Validation Error Response
```json
{
  "status": 400,
  "message": "Validasi gagal",
  "data": {
    "name": "Nama produk tidak boleh kosong",
    "price": "Harga harus lebih dari 0"
  },
  "timestamp": "2026-07-19T21:25:00"
}
```

### Not Found Response
```json
{
  "status": 404,
  "message": "Produk dengan ID 99 tidak ditemukan",
  "data": null,
  "timestamp": "2026-07-19T21:25:00"
}
```

## 🗄️ H2 Database Console

Akses H2 Console di browser: **http://localhost:8080/h2-console**

| Field | Value |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:productdb` |
| Username | `sa` |
| Password | *(kosong)* |

## 🔄 Migrasi ke Database Lain (MySQL/PostgreSQL)

Untuk beralih ke MySQL, ubah `pom.xml` dan `application.properties`:

### pom.xml — Ganti H2 dengan MySQL
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

### application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/productdb
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```
