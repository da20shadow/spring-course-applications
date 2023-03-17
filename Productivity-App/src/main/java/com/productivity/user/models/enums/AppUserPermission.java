package com.productivity.user.models.enums;

public enum AppUserPermission {
    EXERCISE_READ("exercise:read"),
    EXERCISE_WRITE("exercise:write"),
    MEMBER_READ("member:read"),
    MEMBER_WRITE("member:write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
