package com.companion.common.validation;

import com.companion.common.error.ErrorCode;
import com.companion.common.error.ErrorDetail;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ValidationUtil {

    private ValidationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void fieldIsNotNull(List<ErrorDetail> errorsList, Object value, @NotNull String attributeName) {
        if (value == null) {
            var errorDetail = ErrorDetail.builder(ErrorCode.FIELD_REQUIRED)
                    .attribute(attributeName)
                    .build();
            errorsList.add(errorDetail);
        }
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
        if(!acceptableValues.contains(value)) {
            var errorDetail = ErrorDetail.builder(ErrorCode.FIELD_VALUE_NOT_ACCEPTABLE)
                    .attribute(attributeName)
                    .value(value)
                    .args(attributeName, acceptableValues)
                    .build();
            errorsList.add(errorDetail);
        }
    }

    public static void stringMaxLength(List<ErrorDetail> errorsList, @NotNull String value, @NotNull String attributeName, @NotNull int maxLength) {
        if(value.length() > maxLength) {
            var errorDetail = ErrorDetail.builder(ErrorCode.FIELD_SIZE_OUT_OF_RANGE)
                    .attribute(attributeName)
                    .value(value)
                    .args(attributeName, 0, maxLength, value)
                    .build();
            errorsList.add(errorDetail);
        }
    }

    public static void stringLength(List<ErrorDetail> errorsList, @NotNull String value, @NotNull String attributeName,  @NotNull int minLength, @NotNull int maxLength) {
        if(value.length() < minLength || value.length() > maxLength) {
            var errorDetail = ErrorDetail.builder(ErrorCode.FIELD_SIZE_OUT_OF_RANGE)
                    .attribute(attributeName)
                    .value(value)
                    .args(attributeName, minLength, maxLength, value)
                    .build();
            errorsList.add(errorDetail);
        }
    }
}
