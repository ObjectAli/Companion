package com.companion.common.result;

public interface Result<S, F> {
    boolean isSuccess();

    boolean isFailure();

    S getBody();

    F getError();

    static <S, F> Result<S, F> success(S body) {
        return new Success<>(body);
    }

    static <S, F> Result<S, F> failure(F error) {
        return new Failure<>(error);
    }
}