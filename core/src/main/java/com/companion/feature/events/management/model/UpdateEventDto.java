package com.companion.feature.events.management.model;

import com.companion.contracts.model.UpdateObjectService;
import lombok.Getter;

import java.util.UUID;


@Getter
public class UpdateEventDto extends CreateEventDto implements UpdateObjectService {

    private UUID id;
    private Integer currentParticipants;
    private EventStatus status;
}
