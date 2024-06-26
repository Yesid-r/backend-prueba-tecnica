package com.example.pruebatecnica.user;

import com.example.pruebatecnica.model.Credito;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user" , uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;

    private String email;
    @Column(unique = true, nullable = false)
    private String phone;
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne
    private Credito credito;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

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
