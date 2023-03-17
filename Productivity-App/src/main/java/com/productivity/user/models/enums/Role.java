package com.productivity.user.models.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.productivity.user.models.enums.AppUserPermission.*;

public enum Role {
    USER(Sets.newHashSet()),
    ADMIN_TRAINEE(Sets.newHashSet(EXERCISE_READ)),
    ADMIN(Sets.newHashSet(EXERCISE_READ,EXERCISE_WRITE,MEMBER_READ,MEMBER_WRITE));

    private final Set<AppUserPermission> permissions;

    Role(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

}
