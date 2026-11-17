package com.companion.feature.events.get.card.model;

import com.companion.jooq.generated.tables.pojos.Events;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventCardDto {

    private UUID id;
    private String organizer;
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
    private String price;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private String status;
    private Boolean isAdults;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    public static EventCardDto toEventCardDto(Events event) {
//        return EventCardDto.builder()
//                .id(event.getId())
//                .organizer(event.getOrganizerId())
//                .title(event.getTitle())
//                .description(event.getDescription())
//                .category(event.getCategory())
//                .genderRestriction(event.getGenderRestriction().getName())
//                .eventTime(event.getEventTime())
//                .eventEndTime(event.getEventEndTime())
//                .locationName(event.getLocationName())
//                .locationLat(event.getLocationLat())
//                .locationLon(event.getLocationLon())
//                .price(event.getPrice().toString())
//                .maxParticipants(event.getMaxParticipants())
//                .currentParticipants(0)
////                .status(event.getStatus().getLiteral())
//                .createdAt(event.getCreatedAt())
//                .updatedAt(event.getUpdatedAt())
//                .isAdults(event.getIsAdults())
//                .build();
//    }


    @Override
    public String toString() {
        return "EventCardDto{" +
                "id=" + id +
                ", organizer='" + organizer + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", genderRestriction='" + genderRestriction + '\'' +
                ", eventTime=" + eventTime +
                ", eventEndTime=" + eventEndTime +
                ", locationName='" + locationName + '\'' +
                ", locationLat=" + locationLat +
                ", locationLon=" + locationLon +
                ", locationGeo=" + locationGeo +
                ", price='" + price + '\'' +
                ", maxParticipants=" + maxParticipants +
                ", currentParticipants=" + currentParticipants +
                ", status='" + status + '\'' +
                ", isAdults=" + isAdults +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}