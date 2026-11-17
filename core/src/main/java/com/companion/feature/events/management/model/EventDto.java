package com.companion.feature.events.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private UUID id;
    private UUID organizerId;
    private String title;
    private String description;
    private String category;
    private String genderRestriction;
    private LocalDateTime eventTime;
    private LocalDateTime eventEndTime;
    private String locationName;
    private Double locationLat;
    private Double locationLon;
    private Object locationGeo;
    private BigDecimal price;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private EventStatus status;
    private Boolean isAdults;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EventDto toEventDto(CreateEventDto createEventDto, UUID currentOrganizerId){
        return EventDto.builder()
                .id(UUID.randomUUID())
                .organizerId(currentOrganizerId)
                .title(createEventDto.getTitle())
                .description(createEventDto.getDescription())
                .category(createEventDto.getCategory())
                .genderRestriction(createEventDto.getGenderRestriction())
                .eventTime(createEventDto.getEventTime())
                .eventEndTime(createEventDto.getEventEndTime())
                .locationName(createEventDto.getLocationName())
                .locationLat(createEventDto.getLocationLat())
                .locationLon(createEventDto.getLocationLon())
                .price(createEventDto.getPrice())
                .maxParticipants(createEventDto.getMaxParticipants())
                .currentParticipants(0)
                .status(EventStatus.ANNOUNCED)
                .createdAt(LocalDateTime.now())
                .isAdults(createEventDto.getIsAdults())
                .build();
    }

    public static EventDto toEventDto(UpdateEventDto updateEventDto, UUID currentOrganizerId){

        return EventDto.builder()
                .id(updateEventDto.getId())
                .organizerId(currentOrganizerId)
                .title(updateEventDto.getTitle())
                .description(updateEventDto.getDescription())
                .category(updateEventDto.getCategory())
                .genderRestriction(updateEventDto.getGenderRestriction())
                .eventTime(updateEventDto.getEventTime())
                .eventEndTime(updateEventDto.getEventEndTime())
                .locationName(updateEventDto.getLocationName())
                .locationLat(updateEventDto.getLocationLat())
                .locationLon(updateEventDto.getLocationLon())
                .price(updateEventDto.getPrice())
                .maxParticipants(updateEventDto.getMaxParticipants())
                .currentParticipants(updateEventDto.getCurrentParticipants())
                .status(updateEventDto.getStatus())
                .updatedAt(LocalDateTime.now()) //replace to lastModify
                .isAdults(updateEventDto.getIsAdults())
                .build();
    }
}
