package com.security.tasks.models.enums;

public enum TaskStatus {
    TO_DO("To Do"),
    IN_PROGRESS("In Progress"),
    IN_REVISION("In Revision"),
    COMPLETED("Completed");

    private final String formattedStatus;

    private TaskStatus(String formattedStatus) {
        this.formattedStatus = formattedStatus;
    }

    public String getStatusName() {
        return this.formattedStatus;
    }
}

