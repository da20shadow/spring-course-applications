package com.productivity.goal.exception;

public class GoalException extends RuntimeException {

    public GoalException(String message) {
        super(message);
    }

    public static GoalException goalNotFound(Long goalId) {
        return new GoalException("Goal not found with id: " + goalId);
    }

    public static GoalException targetNotFound(Long targetId) {
        return new GoalException("Target not found with id: " + targetId);
    }

    public static GoalException ideaNotFound(Long ideaId) {
        return new GoalException("Idea not found with id: " + ideaId);
    }

    //TODO: Add other common exceptions related to goals here
}

