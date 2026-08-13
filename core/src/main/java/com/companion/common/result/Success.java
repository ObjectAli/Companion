package com.companion.common.result;

// Реализация успеха
public record Success<S, F>(S body) implements Result<S, F> {
    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public S getBody() {
        return body;
    }

    @Override
    public F getError() {
        return null;
    }
}
