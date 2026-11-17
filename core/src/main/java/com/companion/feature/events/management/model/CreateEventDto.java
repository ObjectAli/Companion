package com.companion.feature.events.management.model;

import com.companion.common.error.ErrorDetail;
import com.companion.common.validation.ValidationUtil;
import com.companion.contracts.validators.RequestBodyValidator;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class CreateEventDto implements RequestBodyValidator {

    private String title;
    private String description;
    private String category;
    private String genderRestriction;
    private LocalDateTime eventTime;
    private LocalDateTime eventEndTime;
//    private Integer durationMinutes;
    private String locationName;
    private Double locationLat;
    private Double locationLon;
    private Object locationGeo;
    private BigDecimal price;
    private Integer maxParticipants;
    private Boolean isAdults;

    @Override
    public List<ErrorDetail> validate() {
        return new ValidationProcessor().execute();
    }

    @Override
    public String toString() {
        return "CreateEventDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", genderRestriction='" + genderRestriction + '\'' +
                ", eventTime=" + eventTime +
                ", eventEndTime=" + eventEndTime +
//                ", durationMinutes=" + durationMinutes +
                ", locationName='" + locationName + '\'' +
                ", locationLat=" + locationLat +
                ", locationLon=" + locationLon +
                ", locationGeo=" + locationGeo +
                ", price=" + price +
                ", maxParticipants=" + maxParticipants +
                ", isAdults=" + isAdults +
                '}';
    }

    private class ValidationProcessor {
        private void validateTitle(List<ErrorDetail> errorDetails) {
            final String attributeName = "title";
            ValidationUtil.fieldIsNotNull(errorDetails, title, attributeName);
            ValidationUtil.stringIsNotEmpty(errorDetails, title, attributeName);
            ValidationUtil.stringLength(errorDetails, title, attributeName, 5, 40);
        }

        private void validateDescription(List<ErrorDetail> errorDetails) {
            final String attributeName = "description";
            ValidationUtil.fieldIsNotNull(errorDetails, description, attributeName);
            ValidationUtil.stringIsNotEmpty(errorDetails, description, attributeName);
            ValidationUtil.stringLength(errorDetails, description, attributeName, 10, 200);
        }

        public List<ErrorDetail> execute() {
            List<ErrorDetail> errorsList = new ArrayList<>();

            validateTitle(errorsList);
            validateDescription(errorsList);

            return errorsList;
        }
    }
}
