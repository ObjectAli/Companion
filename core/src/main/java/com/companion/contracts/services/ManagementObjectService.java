package com.companion.contracts.services;

import com.companion.common.result.Result;

import java.util.UUID;

public interface ManagementObjectService<C, U, R> {

    Result<R, String> create(C request, UUID userId);

    Result<R, String> update(U request, UUID userId);

    Result<UUID, String> delete(UUID id, UUID userId);

}
