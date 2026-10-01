package com.companion.feature.events.management.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class EventDto {

    private UUID id;
    private UUID organizerId;
    private String title;
    private String description;
    private String category;
    private LocalDateTime eventTime;
    private Integer durationMinutes;
    private String locationName;
    private Double locationLat;
    private Double locationLon;
    private Object locationGeo;
    private BigDecimal price;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private EventStatus status;
    private Boolean onlyFor18;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EventDto toEventDto(CreateEventDto createEventDto, UUID currentOrganizerId){
        return EventDto.builder()
                .id(UUID.randomUUID())
                .organizerId(currentOrganizerId)
                .title(createEventDto.getTitle())
                .description(createEventDto.getDescription())
                .category(createEventDto.getCategory())
                .eventTime(createEventDto.getEventTime())
                .durationMinutes(createEventDto.getDurationMinutes())
                .locationName(createEventDto.getLocationName())
                .locationLat(createEventDto.getLocationLat())
                .locationLon(createEventDto.getLocationLon())
                .price(createEventDto.getPrice())
                .maxParticipants(createEventDto.getMaxParticipants())
                .currentParticipants(0)
                .status(EventStatus.ANNOUNCED)
                .createdAt(LocalDateTime.now())
                .onlyFor18(createEventDto.getOnlyFor18())
                .build();
    }

    public static EventDto toEventDto(UpdateEventDto updateEventDto, UUID currentOrganizerId){

        return EventDto.builder()
                .id(updateEventDto.getId())
                .organizerId(currentOrganizerId)
                .title(updateEventDto.getTitle())
                .description(updateEventDto.getDescription())
                .category(updateEventDto.getCategory())
                .eventTime(updateEventDto.getEventTime())
                .durationMinutes(updateEventDto.getDurationMinutes())
                .locationName(updateEventDto.getLocationName())
                .locationLat(updateEventDto.getLocationLat())
                .locationLon(updateEventDto.getLocationLon())
                .price(updateEventDto.getPrice())
                .maxParticipants(updateEventDto.getMaxParticipants())
                .currentParticipants(updateEventDto.getCurrentParticipants())
                .status(updateEventDto.getStatus())
                .updatedAt(LocalDateTime.now()) //replace to lastModify
                .onlyFor18(updateEventDto.getOnlyFor18())
                .build();
    }
}
