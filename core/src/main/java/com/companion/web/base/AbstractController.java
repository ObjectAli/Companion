package com.companion.web.base;

import com.companion.common.query.QueryResult;
import com.companion.web.model.ApiResponse;
import com.companion.common.error.ErrorDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class AbstractController {

    private static final String SUCCEEDED = "Succeeded";
    private static final String FAILED = "Failed";

    protected ResponseEntity<ApiResponse<Object>> readRecords(QueryResult<?> queryResult) {
        ApiResponse<Object> response = ApiResponse.builder()
                .data(queryResult)
                .statusCode(200)
                .success(true)
                .message(SUCCEEDED)
                .build();

        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<ApiResponse<Object>> throwError(List<ErrorDetail> errors) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(400)
                .success(false)
                .message(FAILED)
                .errors(errors)
                .build();

        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<ApiResponse<Object>> ok(QueryResult<?> queryResult, int statusCode) {
        ApiResponse<Object> response = ApiResponse.builder()
                .data(queryResult)
                .statusCode(statusCode)
                .success(true)
                .message(SUCCEEDED)
                .build();

        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<ApiResponse<Object>> ok(Object queryResult, int statusCode) {
        ApiResponse<Object> response = ApiResponse.builder()
                .data(queryResult)
                .statusCode(statusCode)
                .success(true)
                .message(SUCCEEDED)
                .build();

        return ResponseEntity.ok(response);
    }
}
