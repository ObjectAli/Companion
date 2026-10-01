package com.companion.web.model;

import com.companion.common.error.ErrorDetail;
import com.companion.common.validation.ValidationUtil;
import com.companion.contracts.validators.RequestBodyValidator;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DisplayRecordsRequest implements RequestBodyValidator {

    protected String sortBy = "date";
    protected String sortDirection = "desc";
    protected Integer offset = 0;
    protected Integer page = 0;
    protected Integer size = 20;

    @Override
    public List<ErrorDetail> validate() {
        List<ErrorDetail> errorsList = new ArrayList<>();
        validateSortBy(errorsList);
        validateSortDirection(errorsList);

        return errorsList;
    }

    protected void validateSortBy(List<ErrorDetail> errorsList){
        if (sortBy != null) {
            ValidationUtil.stringIsNotEmpty(errorsList, sortBy, "sortBy");
            ValidationUtil.stringContainInvalidValue(errorsList, sortBy, "sortBy", List.of("date"));
        }
    }

    protected void validateSortDirection(List<ErrorDetail> errorsList) {
        if (sortDirection != null) {
            ValidationUtil.stringIsNotEmpty(errorsList, sortDirection, "sortDirection");
            ValidationUtil.stringContainInvalidValue(errorsList, sortDirection, "sortDirection", List.of("asc", "desc"));
        }
    }
}