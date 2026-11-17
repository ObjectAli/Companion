package com.companion.web.special.types;

import com.companion.common.error.ErrorDetail;
import com.companion.common.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class IdDto {

    UUID id;

    public static IdDto valueOf(String id) {
        return new IdDto(UUID.fromString(id));
    }

    public List<ErrorDetail> validate() {
        List<ErrorDetail> errors = new ArrayList<>();

        ValidationUtil.fieldIsNotNull(errors, id, "id");
        ValidationUtil.stringIsNotEmpty(errors, id.toString(), "id");

        return errors;
    }
}
