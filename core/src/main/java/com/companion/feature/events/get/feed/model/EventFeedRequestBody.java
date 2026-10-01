package com.companion.feature.events.get.feed.model;

import com.companion.common.error.ErrorDetail;
import com.companion.common.validation.ValidationUtil;
import com.companion.web.model.DisplayRecordsRequest;
import com.companion.web.special.types.FieldSimpleList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class EventFeedRequestBody extends DisplayRecordsRequest {

    private FieldSimpleList<String> category;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime toDate;

    private Double lat;
    private Double lon;
    private Double radiusKm;

    @Override
    public List<ErrorDetail> validate() {
        List<ErrorDetail> errorsList = new ArrayList<>();
        validateSortBy(errorsList);
        validateSortDirection(errorsList);

        return errorsList;
    }

    @Override
    protected void validateSortBy(List<ErrorDetail> errorsList) {
        if (sortBy != null) {
            ValidationUtil.stringIsNotEmpty(errorsList, sortBy, "sortBy");
            ValidationUtil.stringContainInvalidValue(errorsList, sortBy, "sortBy", List.of("date", "price", "distance"));
        }
    }

    @Override
    public String toString() {
        return "EventFeedRequestBody{" +
                "category=" + category +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", lat=" + lat +
                ", lon=" + lon +
                ", radiusKm=" + radiusKm +
                ", sortBy='" + sortBy + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                ", offset=" + offset +
                ", page=" + page +
                ", size=" + size +
                '}';
    }
}
