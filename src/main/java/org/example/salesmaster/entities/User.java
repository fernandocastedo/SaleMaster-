package org.example.salesmaster.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User Entity
 * -----------------------------------------------------
 * ✔ Representa un usuario del sistema SalesMaster
 * ✔ Implementa UserDetails para integración con Spring Security
 * ✔ Incluye rol (ADMIN o VENDEDOR)
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    /**
     * Rol del usuario (ADMIN o VENDEDOR)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.VENDEDOR;

    /**
     * ✅ Devuelve la lista de roles del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve siempre una autoridad con prefijo ROLE_ para Spring Security
        return List.of(new SimpleGrantedAuthority("ROLE_" + (role != null ? role.name() : Role.VENDEDOR.name())));
    }

    /**
     * ✅ El username para Spring Security será el email
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * ✅ Indica si la cuenta está activa
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

