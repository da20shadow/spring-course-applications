package com.security.auth.jwt;

import com.security.auth.user.models.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(unique = true)
    @Basic
    public String token;

    @Enumerated(EnumType.STRING)
    public JwtTokenType tokenType = JwtTokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
