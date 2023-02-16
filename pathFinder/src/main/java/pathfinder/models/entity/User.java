package pathfinder.models.entity;

import jakarta.persistence.*;
import pathfinder.models.enums.LEVEL;
import pathfinder.models.enums.Role;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    private int age;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private LEVEL level;

    public User() {
        this.role = Role.USER;
        this.level = LEVEL.BEGINNER;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public LEVEL getLevel() {
        return level;
    }

    public User setLevel(LEVEL level) {
        this.level = level;
        return this;
    }
}
