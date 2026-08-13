package com.companion.common.validation;

import com.companion.common.error.ErrorCode;
import com.companion.common.error.ErrorDetail;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ValidationUtil {

    private ValidationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void stringIsNotEmpty(List<ErrorDetail> errorsList, @NotNull String value, @NotNull String attributeName) {
        if(value.isEmpty()) {
            var errorDetail = ErrorDetail.builder(ErrorCode.FIELD_REQUIRED)
                    .attribute(attributeName)
                    .build();
            errorsList.add(errorDetail);
        }
    }

    public static void stringContainInvalidValue(List<ErrorDetail> errorsList, @NotNull String value, @NotNull String attributeName, List<String> acceptableValues) {
        if(acceptableValues.contains(value)) {
            var errorDetail = ErrorDetail.builder(ErrorCode.FIELD_VALUE_NOT_ACCEPTABLE)
                    .attribute(attributeName)
                    .value(value)
                    .args(value, acceptableValues)
                    .build();
            errorsList.add(errorDetail);
        }
    }
}
