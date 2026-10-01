package com.companion.feature.account;

import com.companion.common.result.Result;
import com.companion.contracts.services.ManagementObjectService;
import com.companion.feature.account.model.AccountDto;
import com.companion.feature.account.model.CreateAccountDto;
import com.companion.feature.account.model.UpdateAccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountManagementService implements ManagementObjectService<CreateAccountDto, UpdateAccountDto, AccountDto> {

    private static final Logger log = LoggerFactory.getLogger(AccountManagementService.class);

    private final AccountCommandRepository accountCommandRepository;

    public AccountManagementService(AccountCommandRepository accountCommandRepository) {
        this.accountCommandRepository = accountCommandRepository;
    }

    @Override
    public Result<AccountDto, String> create(CreateAccountDto dto, UUID userId) {
        log.info("Creating account with username '{}'", dto.getUsername());

        AccountDto accountDto = AccountDto.toAccountDto(dto);

        var createResult = accountCommandRepository.create(accountDto);

        if (createResult.isFailure()) {
            log.debug("Creating account by object: {} is failed. Error: {}", dto, createResult.getError());
        }

        log.info("Creating account with username '{}' is complete", dto.getUsername());
        return createResult;
    }

    @Override
    public Result<AccountDto, String> update(UpdateAccountDto dto, UUID userId) {
        log.info("Updating account with username '{}'", dto.getUsername());

        AccountDto accountDto = AccountDto.toAccountDto(dto);

        var updateResult = accountCommandRepository.update(accountDto, dto.getUpdatedFields());

        if (updateResult.isFailure()) {
            log.debug("Updating account by object: {} is failed. Error: {}", dto, updateResult.getError());
        }

        log.info("Updating account with username '{}' is complete", dto.getUsername());
        return updateResult;
    }

    @Override
    public Result<UUID, String> delete(UUID id, UUID userId) {
        log.info("Deleting account with id '{}'", id);

        var updateResult = accountCommandRepository.delete(id);

        if (updateResult.isFailure()) {
            log.debug("Deleting account with id: {} is failed. Error: {}", id, updateResult.getError());
        }

        log.info("Updating account with id '{}' is complete", id);
        return updateResult;
    }
}
