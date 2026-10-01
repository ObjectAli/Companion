package com.companion.feature.account;

import com.companion.common.AbstractRepository;
import com.companion.common.result.Result;
import com.companion.contracts.exceptions.DatabaseQueryException;
import com.companion.feature.account.model.AccountDto;
import com.companion.jooq.generated.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

import static com.companion.jooq.generated.tables.Users.USERS;

@Repository
public class AccountCommandRepository extends AbstractRepository {

    public AccountCommandRepository(DSLContext dslContext) {
        super(dslContext);
    }

    private static final Logger log = LoggerFactory.getLogger(AccountCommandRepository.class);

    public Result<AccountDto, String> create(AccountDto dto) {
        log.info("Create account record with name {}", dto.getUsername());

        try {
            log.info("Account record set id {}", 1L);

            UsersRecord rec = dslContext.newRecord(USERS, dto);
            rec.insert();

            AccountDto resDto = rec.into(AccountDto.class);

            log.info("Account record '{}' with id {} is created", dto.getUsername(), 1L);

            return Result.success(resDto);

        } catch (Exception ex) {
            log.error("The creation of an account record '{}' with id {} failed with an error: {}", dto.getUsername(), dto.getId(), ex.getMessage());

            return Result.failure(ex.getMessage());
        }
    }

    public Result<AccountDto, String> update(AccountDto dto, Set<String> updatedFields) {
        log.info("Update account record with username {}", dto.getUsername());

        try {
            UUID updatingRecordId = dto.getId();

            //Проверка, что такая запись существует в БД
            UsersRecord originalRecord = getRecord(updatingRecordId);
            if (originalRecord == null) {
                throw new DatabaseQueryException(String.format("Account with id %s was not found", updatingRecordId));
            }

            UsersRecord newRecord = dslContext.newRecord(USERS, dto);

            //Проверка, что в новой записи будут изменения
            if (updatedFields != null && !updatedFields.isEmpty()) {
                boolean recHasChanges = setChangedForUpdatedFieldsOnly(newRecord, originalRecord, updatedFields);
                if (!recHasChanges) {
                    throw new DatabaseQueryException(String.format("The updating record with id %s is not have changes", updatingRecordId));
                }
            }

            int count = dslContext
                    .update(USERS)
                    .set(newRecord)
                    .where(USERS.ID.eq(updatingRecordId))
                    .execute();

            //Проверка, что обновится только 1 запись
            if (count != 1)
                throw new DatabaseQueryException(String.format("The number of records to be updated with id %s is not equal to one", updatingRecordId));

            AccountDto dtoAfterUpdate = getRecord(updatingRecordId).into(AccountDto.class);

            log.info("Account record with username '{}' and id {} is updated", dto.getUsername(), dto.getId());

            return Result.success(dtoAfterUpdate);

        } catch (Exception ex) {
            log.error("The update of the account record '{}' with id {} failed with an error: {}", dto.getUsername(), dto.getId(), ex.getMessage());

            return Result.failure(ex.getMessage());
        }
    }

    private UsersRecord getRecord(UUID uuid) {
        log.debug("Get account record by id {} ", uuid);

        var rec = (UsersRecord) dslContext
                .select()
                .from(USERS)
                .where(USERS.ID.eq(uuid))
                .fetchOne();

        if (rec != null) {
            log.debug("Account record with id {} has been found, username: {}", rec.getUsername(), uuid);
        } else {
            log.error("Account record with id {} was not found!", uuid);
        }
        return rec;
    }

    public Result<UUID, String> delete(UUID id) {
        log.info("Delete account record with id {}", id);

        int count;
        try {
            count = dslContext
                    .delete(USERS)
                    .where(USERS.ID.eq(id))
                    .execute();

            if (count != 1) {
                throw new DatabaseQueryException(String.format("The number of account records to be deleted with id %s is not equal to one", id));
            }
        } catch (Exception ex) {
            log.error("The delete of the account record with id {} failed with an error: {}", id, ex.getMessage());

            return Result.failure(ex.getMessage());
        }
        log.info("Account record with id {} is deleted", id);

        return Result.success(id);
    }
}
