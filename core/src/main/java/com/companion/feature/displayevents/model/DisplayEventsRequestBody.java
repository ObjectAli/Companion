package com.companion.feature.displayevents.model;

import com.companion.common.error.ErrorDetail;
import com.companion.common.validation.ValidationUtil;
import com.companion.web.model.DisplayRecordsRequest;
import com.companion.web.special.types.FieldSimpleList;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DisplayEventsRequestBody extends DisplayRecordsRequest {

    private FieldSimpleList<String> category;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime toDate;

    private Double lat;
    private Double lon;
    private Double radiusKm;

    private final List<String> sortByAcceptableValues = List.of("date", "price", "distance");

    @Override
    public List<ErrorDetail> validate() {
        List<ErrorDetail> errorsList = new ArrayList<>();
        validateSortBy(errorsList);
        validateSortDirection(errorsList);

        return errorsList;
    }

    protected void validateSortBy(List<ErrorDetail> errorsList){
        ValidationUtil.stringIsNotEmpty(errorsList, sortBy, "sortBy");
        ValidationUtil.stringContainInvalidValue(errorsList, sortBy, "sortBy", sortByAcceptableValues);
    }

    @Override
    public String toString() {
        return "DisplayEventsRequestBody{" +
                "category=" + category +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", lat=" + lat +
                ", lon=" + lon +
                ", radiusKm=" + radiusKm +
                ", sortByAcceptableValues=" + sortByAcceptableValues +
                ", sortBy='" + sortBy + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                ", offset=" + offset +
                ", page=" + page +
                ", size=" + size +
                ", sortByAcceptableValues=" + sortByAcceptableValues +
                ", sortDirectionAcceptableValues=" + sortDirectionAcceptableValues +
                '}';
    }
}
