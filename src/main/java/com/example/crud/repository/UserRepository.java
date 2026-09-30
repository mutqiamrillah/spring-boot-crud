package com.example.crud.repository;

import com.example.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Untuk mencari user berdasarkan username saat proses login
    Optional<User> findByUsername(String username);
    
    // Untuk mengecek apakah username sudah dipakai saat proses registrasi
    boolean existsByUsername(String username);
}
