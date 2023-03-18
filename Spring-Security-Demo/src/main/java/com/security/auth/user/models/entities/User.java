package com.security.auth.user.models.entities;

import com.security.auth.jwt.Token;
import com.security.auth.user.models.enums.UserRole;
import com.security.shared.models.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @NotBlank(message = "First name cannot be empty!")
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @NotBlank(message = "Email cannot be empty!")
    @Pattern(regexp = "^[a-z]+[a-z0-9_]+[@][a-z]{3,}[.][a-z]{2,7}$",
            message = "Please, enter valid email format!")
    @Email
    @Column(name = "email", unique = true, nullable = false, length = 145)
    private String email;

    @NotBlank(message = "Password cannot be empty!")
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
