package com.security.auth.user.models.enums;

public enum UserPermission {
    MEMBER_READ("member:read"),
    MEMBER_WRITE("member:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
