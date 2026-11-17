package com.companion.feature.events.get.card;

import com.companion.common.result.Result;
import com.companion.feature.events.get.card.model.EventCardDto;
import com.companion.jooq.generated.tables.pojos.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EventCardService {

    private static final Logger log = LoggerFactory.getLogger(EventCardService.class);
    private static final String LOG_PREFIX = "Service> GET event card";

    private final EventCardRepository eventCardRepository;

    public EventCardService(EventCardRepository eventCardRepository) {
        this.eventCardRepository = eventCardRepository;
    }

    @Transactional(readOnly = true)
    public Result<EventCardDto, String> get(UUID id) {

        log.info("{} by id: '{}' start", LOG_PREFIX, id);

        //осуществление выборки
        Result<EventCardDto, String> queryResult = eventCardRepository.get(id);

        if (queryResult.isSuccess()) {
            log.info("{} by id: '{}' was completed successfully.", LOG_PREFIX, id);

//            EventCardDto dto = EventCardDto.toEventCardDto(queryResult.getBody());
            return Result.success(queryResult.getBody());
        }
        else return Result.failure(queryResult.getError());
    }
}