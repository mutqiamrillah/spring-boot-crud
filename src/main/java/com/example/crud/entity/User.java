package com.example.crud.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users") // Nama tabel di database
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // --- Method dari antarmuka UserDetails Spring Security ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mengembalikan role user (dengan prefix ROLE_ yang disyaratkan Spring Security)
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Akun tidak kadaluarsa
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Akun tidak terkunci
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Password tidak kadaluarsa
    }

    @Override
    public boolean isEnabled() {
        return true; // Akun aktif
    }
}
