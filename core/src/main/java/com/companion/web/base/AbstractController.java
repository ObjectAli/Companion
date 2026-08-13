package com.companion.web.base;

import com.companion.common.query.QueryResult;
import com.companion.web.model.ApiResponse;
import com.companion.common.error.ErrorDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class AbstractController {

    protected ResponseEntity<ApiResponse<Object>> readRecords(QueryResult<?> queryResult) {
        ApiResponse<Object> response = ApiResponse.builder()
                .data(queryResult)
                .statusCode(200)
                .message("Succeeded")
                .build();

        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<ApiResponse<Object>> throwError(List<ErrorDetail> errors) {
        ApiResponse<Object> response = ApiResponse.builder()
                .statusCode(400)
                .message("Failed")
                .errors(errors)
                .build();

        return ResponseEntity.ok(response);
    }
}
