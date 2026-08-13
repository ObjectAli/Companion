package com.companion.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class SearchEventsRequest {

    @Pattern(regexp = "^(football|movie|coffee|hiking)?$",
            message = "Invalid category")
    private String category;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime toDate;

    @Min(-90)
    private Double lat;

    @Min(-180)
    private Double lon;

    @Min(1)
    private Double radiusKm;

    @Pattern(regexp = "^(date|price|distance)?$")
    private String sortBy = "date";

    @Pattern(regexp = "^(asc|desc)?$")
    private String sortDirection = "desc";

    @Min(0)
    private Integer page = 0;

    @Min(1)
    private Integer size = 20;
}
