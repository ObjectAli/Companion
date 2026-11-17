package com.companion.feature.events.get.card;

import com.companion.common.result.Result;
import com.companion.feature.events.get.card.model.EventCardDto;
import com.companion.jooq.generated.tables.pojos.Events;
import com.companion.web.base.AbstractController;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.companion.jooq.generated.Tables.USERS;
import static com.companion.jooq.generated.tables.Events.EVENTS;

@Repository
public class EventCardRepository extends AbstractController {

    private final DSLContext dslContext;

    public EventCardRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private static final Logger log = LoggerFactory.getLogger(EventCardRepository.class);
    private static final String LOG_PREFIX = "Repository> GET event card";

    public Result<EventCardDto, String> get(UUID id) {
        log.info("{} by id: '{}' start", LOG_PREFIX, id);

        var record = dslContext
                .select(EVENTS.asterisk(),
                        USERS.USERNAME.as("organizer"))
                .from(EVENTS)
                .leftJoin(USERS).on(USERS.ID.eq(EVENTS.ORGANIZER_ID))
                .where(EVENTS.ID.equal(id))
                .fetchOneInto(EventCardDto.class);

        if (record == null) {
            log.debug("{} by id: '{}' was completed - record not found.", LOG_PREFIX, id);
            return Result.failure(String.format("Event record with id %s not found.", id));
        }

        log.info("{} by id: '{}' was completed successfully.", LOG_PREFIX, id);

        return Result.success(record);
    }
}