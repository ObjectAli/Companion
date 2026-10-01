package com.companion.feature.events.management;

import com.companion.common.result.Result;
import com.companion.contracts.services.ManagementObjectService;
import com.companion.feature.events.management.model.CreateEventDto;
import com.companion.feature.events.management.model.EventDto;
import com.companion.feature.events.management.model.UpdateEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventManagementService implements ManagementObjectService<CreateEventDto, UpdateEventDto, EventDto> {

    private static final Logger log = LoggerFactory.getLogger(EventManagementService.class);

    private final EventCommandRepository eventCommandRepository;

    public EventManagementService(EventCommandRepository eventCommandRepository) {
        this.eventCommandRepository = eventCommandRepository;
    }

    @Override
    public Result<EventDto, String> create(CreateEventDto dto, UUID userId) {
        log.info("Creating event with name '{}'", dto.getTitle());

        EventDto eventDto = EventDto.toEventDto(dto, userId);

        var createResult = eventCommandRepository.create(eventDto);

        if (createResult.isFailure()) {
            log.debug("Creating event by object: {} is failed. Error: {}", dto, createResult.getError());
        }

        log.info("Creating event with name '{}' is complete", dto.getTitle());
        return createResult;
    }

    @Override
    public Result<EventDto, String> update(UpdateEventDto dto, UUID userId) {
        log.info("Updating event with name '{}'", dto.getTitle());

        EventDto eventDto = EventDto.toEventDto(dto, userId);

        var updateResult = eventCommandRepository.update(eventDto, dto.getUpdatedFields());

        if (updateResult.isFailure()) {
            log.debug("Updating event by object: {} is failed. Error: {}", dto, updateResult.getError());
        }

        log.info("Updating event with name '{}' is complete", dto.getTitle());
        return updateResult;
    }

    @Override
    public Result<UUID, String> delete(UUID id, UUID userId) {
        log.info("Deleting event with id '{}'", id);

        var updateResult = eventCommandRepository.delete(id);

        if (updateResult.isFailure()) {
            log.debug("Deleting event with id: {} is failed. Error: {}", id, updateResult.getError());
        }

        log.info("Updating event with id '{}' is complete", id);
        return updateResult;
    }
}
