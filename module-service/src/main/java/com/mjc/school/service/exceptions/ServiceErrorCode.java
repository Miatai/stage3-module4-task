package com.mjc.school.service.exceptions;

public enum ServiceErrorCode {
    NEWS_ID_DOES_NOT_EXIST(Constants.ERROR_000001, "News with id %d does not exist."),
    AUTHOR_ID_DOES_NOT_EXIST(Constants.ERROR_000002, "Author with id %d does not exist."),
    TAG_ID_DOES_NOT_EXIST(Constants.ERROR_000003, "Tag with id %d does not exist."),
    COMMENT_ID_DOES_NOT_EXIST(Constants.ERROR_000004, "Comment with id %d does not exist."),
    AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID(Constants.ERROR_000005, "Author not found for news with id %d."),
    VALIDATION(Constants.ERROR_000013, "Validation failed: %s");

    private final String errorMessage;

    ServiceErrorCode(String errorCode, String message) {
        this.errorMessage = "ERROR_CODE: " + errorCode + " ERROR_MESSAGE: " + message;
    }

    public String getMessage() {
        return errorMessage;
    }

    private static class Constants {
        private static final String ERROR_000001 = "000001";
        private static final String ERROR_000002 = "000002";
        private static final String ERROR_000003 = "000003";
        private static final String ERROR_000004 = "000004";
        private static final String ERROR_000005 = "000005";
        private static final String ERROR_000013 = "000013";

        private Constants() {
        }
    }
}
