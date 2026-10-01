package com.companion.feature.events.management;

import com.companion.common.AbstractRepository;
import com.companion.common.result.Result;
import com.companion.contracts.exceptions.DatabaseQueryException;
import com.companion.feature.events.management.model.EventDto;
import com.companion.jooq.generated.tables.records.EventsRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

import static com.companion.jooq.generated.tables.Events.EVENTS;

@Repository
public class EventCommandRepository extends AbstractRepository {

    public EventCommandRepository(DSLContext dslContext) {
        super(dslContext);
    }

    private static final Logger log = LoggerFactory.getLogger(EventCommandRepository.class);

    public Result<EventDto, String> create(EventDto createEvent) {
        log.info("Create event record with title {}", createEvent.getTitle());

        try {
            log.info("Event record set id {}", 1L);

            EventsRecord rec = dslContext.newRecord(EVENTS, createEvent);
            rec.insert();

            EventDto resDto = rec.into(EventDto.class);

            log.info("Event record '{}' with id {} is created", createEvent.getTitle(), 1L);

            return Result.success(resDto);

        } catch (Exception ex) {
            log.error("The creation of an event record '{}' with id {} failed with an error: {}", createEvent.getTitle(), 1L, ex.getMessage());

            return Result.failure(ex.getMessage());
        }
    }

    public Result<EventDto, String> update(EventDto updateEvent, Set<String> updatedFields) {
        log.info("Update event record with title {}", updateEvent.getTitle());

        try {
            UUID updatingRecordId = updateEvent.getId();

            //Проверка, что такая запись существует в БД
            EventsRecord originalRecord = getRecord(updatingRecordId);
            if (originalRecord == null) {
                throw new DatabaseQueryException(String.format("Event with id %s was not found", updatingRecordId));
            }

            EventsRecord newRecord = dslContext.newRecord(EVENTS, updateEvent);

            //Проверка, что в новой записи будут изменения
            if (updatedFields != null && !updatedFields.isEmpty()) {
                boolean recHasChanges = setChangedForUpdatedFieldsOnly(newRecord, originalRecord, updatedFields);
                if (!recHasChanges) {
                    throw new DatabaseQueryException(String.format("The updating record with id %s is not have changes", updatingRecordId));
                }
            }

            int count = dslContext
                    .update(EVENTS)
                    .set(newRecord)
                    .where(EVENTS.ID.eq(updatingRecordId))
                    .execute();

            //Проверка, что обновится только 1 запись
            if (count != 1)
                throw new DatabaseQueryException(String.format("The number of event records to be updated with id %s is not equal to one", updatingRecordId));

            EventDto eventAfterUpdate = getRecord(updatingRecordId).into(EventDto.class);

            log.info("Event record '{}' with id {} is update", updateEvent.getTitle(), 1L);

            return Result.success(eventAfterUpdate);

        } catch (Exception ex) {
            log.error("The update of the event record '{}' with id {} failed with an error: {}", updateEvent.getTitle(), 1L, ex.getMessage());

            return Result.failure(ex.getMessage());
        }
    }

    private EventsRecord getRecord(UUID uuid) {
        log.debug("Get event record by id {} ", uuid);

        var rec = (EventsRecord) dslContext
                .select()
                .from(EVENTS)
                .where(EVENTS.ID.eq(uuid))
                .fetchOne();

        if (rec != null) {
            log.debug("Event record with id {} has been found, title: {}", rec.getTitle(), uuid);
        } else {
            log.error("Event record with id {} was not found!", uuid);
        }
        return rec;
    }

    public Result<UUID, String> delete(UUID id) {
        log.info("Delete event record with id {}", id);

        int count;
        try {
            count = dslContext
                    .delete(EVENTS)
                    .where(EVENTS.ID.eq(id))
                    .execute();

            if (count != 1) {
                throw new DatabaseQueryException(String.format("The number of records to be deleted with id %s is not equal to one", id));
            }
        } catch (Exception ex) {
            log.error("The delete of the event record with id {} failed with an error: {}", id, ex.getMessage());

            return Result.failure(ex.getMessage());
        }
        log.info("Event record with id {} is deleted", id);

        return Result.success(id);
    }
}