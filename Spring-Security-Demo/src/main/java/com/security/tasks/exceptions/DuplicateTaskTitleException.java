package com.security.tasks.exceptions;

public class DuplicateTaskTitleException extends TaskException {
    public DuplicateTaskTitleException(String message) {
        super(message);
    }
}
