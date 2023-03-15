package com.productivity.user.models.entities;

import com.productivity.goal.models.entities.Goal;
import com.productivity.idea.models.entities.Idea;
import com.productivity.shared.models.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotBlank(message = "First name cannot be empty!")
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @NotBlank(message = "Email cannot be empty!")
    @Pattern(regexp = "^[a-z]+[a-z0-9_]+[@][a-z]{3,}[.][a-z]{2,7}$",
            message = "Please, enter valid email format!")
    @Email
    @Column(name = "email", unique = true, nullable = false, length = 145)
    private String email;

    @NotBlank(message = "Password cannot be empty!")
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Idea> ideas = new ArrayList<>();


}

