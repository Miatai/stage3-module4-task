package com.mjc.school.helper;

import java.util.Map;

import static com.mjc.school.helper.Operations.*;

public record Command(
    int operation,
    Map<String, String> params,
    String body,
    Map<String, Object> queryParams) {

    public static Builder builder() {
        return new Builder();
    }

    public static final Command NOT_FOUND = builder().operation(-1).build();

    public static final Command GET_NEWS = builder().operation(GET_ALL_NEWS.getOperationNumber()).build();
    public static final Command GET_AUTHORS = builder().operation(GET_ALL_AUTHORS.getOperationNumber()).build();
    public static final Command GET_TAGS = builder().operation(GET_ALL_TAGS.getOperationNumber()).build();
    public static Command GET_COMMENTS = builder().operation(GET_ALL_COMMENTS.getOperationNumber()).build();


    public static class Builder {
        private int operation;
        private Map<String, String> params;
        private String body;
        private Map<String, Object> queryParams;

        public Builder operation(int operation) {
            this.operation = operation;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder queryParams(Map<String, Object> queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        public Command build() {
            return new Command(operation, params, body, queryParams);
        }
    }
}
