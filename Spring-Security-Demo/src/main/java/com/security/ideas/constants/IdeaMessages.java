package com.security.ideas.constants;

public class IdeaMessages {
    public static class IdeaErrorMessages {
        public static final String ADD_ERROR = "Failed to add idea.";
        public static final String UPDATE_ERROR = "Failed to update idea.";
        public static final String NOTHING_TO_UPDATE_ERROR = "Nothing to update.";
        public static final String DUPLICATE_TITLE_ERROR = "There is already idea with this title.";
        public static final String DELETE_ERROR = "Failed to delete idea.";
        public static final String IDEA_NOT_FOUND_ERROR = "Idea not found.";
        public static final String NO_IDEAS_YET_ERROR = "No Ideas yet.";
        public static final String UNAUTHORIZED_ERROR = "You are not authorized to perform this action.";
        public static final String FORBIDDEN_ERROR = "Access to the requested resource is forbidden.";
        public static final String NO_IDEAS_BY_TAG = "There are no ideas by this tag!";
    }

    public static class IdeaSuccessMessages {
        public static final String ADD_SUCCESS = "Idea added successfully.";
        public static final String UPDATE_SUCCESS = "Idea updated successfully.";
        public static final String DELETE_SUCCESS = "Idea deleted successfully.";
    }
}

