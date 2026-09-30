package com.example.crud.controller;

import com.example.crud.dto.ApiResponse;
import com.example.crud.dto.request.LoginRequest;
import com.example.crud.dto.request.RegisterRequest;
import com.example.crud.dto.response.JwtResponse;
import com.example.crud.entity.Role;
import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import com.example.crud.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "API untuk Autentikasi (Login & Register)")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Melakukan autentikasi dengan username & password untuk mendapatkan JWT token.")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Spring Security akan memanggil CustomUserDetailsService untuk mencocokkan password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Pembuatan string token menggunakan utility
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUsername(), role);
        return ResponseEntity.ok(ApiResponse.success("Login berhasil", jwtResponse));
    }

    @PostMapping("/register")
    @Operation(summary = "Daftar user baru", description = "Mendaftarkan user baru ke dalam database.")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        
        // Cek apakah username sudah ada
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "Error: Username sudah digunakan!"));
        }

        // Buat user baru (Penting: Password di-enkripsi dengan BCrypt)
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER) // Kita jadikan semua pendaftar baru sebagai USER biasa
                .build();

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Berhasil mendaftarkan user baru!", null));
    }
}
