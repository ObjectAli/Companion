package com.companion.feature.account.model;

import com.companion.contracts.model.UpdateObjectService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Data
@Getter
@EqualsAndHashCode(callSuper = false)
public class UpdateAccountDto extends CreateAccountDto implements UpdateObjectService {

    private UUID id;
}
