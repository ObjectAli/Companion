package com.companion.feature.displayevents.model;

import com.companion.common.error.ErrorDetail;
import com.companion.contracts.validators.SpecificationValidator;
import com.companion.web.special.types.FieldSimpleList;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DisplayEventsSpecificationBody implements SpecificationValidator {

    private final FieldSimpleList<String> category;
    private final LocalDateTime fromDate;
    private final LocalDateTime toDate;
    private final Double lon;
    private final Double radiusKm;
    private final String sortBy;
    private final String sortDirection;
    private final Integer page;
    private final Integer size;

    private DisplayEventsSpecificationBody(Builder builder) {
        this.category = builder.category;
        this.fromDate = builder.fromDate;
        this.toDate = builder.toDate;
        this.lon = builder.lon;
        this.radiusKm = builder.radiusKm;
        this.sortBy = builder.sortBy;
        this.sortDirection = builder.sortDirection;
        this.page = builder.page;
        this.size = builder.size;
    }

    public static class Builder {
        private FieldSimpleList<String> category;
        private LocalDateTime fromDate;
        private LocalDateTime toDate;
        private Double lon;
        private Double radiusKm;
        private String sortBy;
        private String sortDirection;
        private Integer page;
        private Integer size;

        public Builder category(FieldSimpleList<String> category) {
            this.category = category;
            return this;
        }

        public Builder fromDate(LocalDateTime fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public Builder toDate(LocalDateTime toDate) {
            this.toDate = toDate;
            return this;
        }

        public Builder lon(Double lon) {
            this.lon = lon;
            return this;
        }

        public Builder radiusKm(Double radiusKm) {
            this.radiusKm = radiusKm;
            return this;
        }

        public Builder sortBy(String sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public Builder sortDirection(String sortDirection) {
            this.sortDirection = sortDirection;
            return this;
        }

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder size(Integer size) {
            this.size = size;
            return this;
        }

        public DisplayEventsSpecificationBody build() {
            return new DisplayEventsSpecificationBody(this);
        }
    }

    public static DisplayEventsSpecificationBody toSpecification(DisplayEventsRequestBody request) {

        return new Builder()
                .category(request.getCategory())
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .lon(request.getLon())
                .radiusKm(request.getRadiusKm())
                .sortBy((request.getSortBy()))
                .sortDirection(request.getSortDirection())
                .page(request.getPage())
                .size(request.getSize())
                .build();
    }

    @Override
    public List<ErrorDetail> validate() {
        return null;
    }
}
