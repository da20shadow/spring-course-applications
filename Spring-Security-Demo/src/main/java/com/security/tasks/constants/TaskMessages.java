package com.security.tasks.constants;

public class TaskMessages {

    public static class ErrorMessages {
        public static final String NOT_FOUND = "Task not found";
        public static final String ADD_ERROR = "Failed to add task. Please check that all required fields are filled and try again.";
        public static final String UPDATE_ERROR = "Failed to update task. Please check that all required fields are filled and try again.";
        public static final String DELETE_ERROR = "Failed to delete task. Please check that the task exists and try again.";
        public static final String GET_RECENT_ERROR = "Failed to retrieve recently added tasks. Please try again later.";
        public static final String GET_IMPORTANT_ERROR = "Failed to retrieve important tasks. Please try again later.";
        public static final String GET_URGENT_ERROR = "Failed to retrieve urgent tasks. Please try again later.";
        public static final String GET_BY_TARGET_ERROR = "Failed to retrieve tasks by target. Please check that the target exists and try again.";
        public static final String DUPLICATE_TITLE_ERROR = "Task with such title already exist!";
        public static final String DUPLICATE_CHECKLIST_ITEM_TITLE_ERROR = "Item with such title already added!";
        public static final String CHECKLIST_ITEM_NOT_FOUND = "Checklist item not found!";
        public static final String NO_CHANGE = "There is no any change!";
        public static final String NO_TODAY_TASKS = "No tasks for today.";
    }

    public static class SuccessMessages {
        public static final String ADD_SUCCESS = "Task added successfully.";
        public static final String UPDATE_SUCCESS = "Task updated successfully.";
        public static final String DELETE_SUCCESS = "Task deleted successfully.";
        public static final String GET_All_SUCCESS = "All added tasks retrieved successfully.";
        public static final String GET_IMPORTANT_SUCCESS = "Important tasks retrieved successfully.";
        public static final String GET_URGENT_SUCCESS = "Urgent tasks retrieved successfully.";
        public static final String GET_BY_TARGET_SUCCESS = "Tasks by target retrieved successfully.";
        public static final String ADD_CHECKLIST_ITEM_SUCCESS = "Successfully added checklist item!";
        public static final String DELETE_CHECKLIST_ITEM_SUCCESS = "Successfully deleted checklist item.";
        public static final String UPDATE_CHECKLIST_ITEM_SUCCESS = "Successfully updated checklist item!";
    }
}
