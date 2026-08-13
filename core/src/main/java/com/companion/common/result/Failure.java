package com.companion.common.result;

// Реализация ошибки
public record Failure<S, F>(F error) implements Result<S, F> {
    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public S getBody() {
        return null;
    }

    @Override
    public F getError() {
        return error;
    }
}
