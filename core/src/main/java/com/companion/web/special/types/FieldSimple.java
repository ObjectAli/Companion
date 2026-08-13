package com.companion.web.special.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FieldSimple<O> {

    O value;

    public boolean isDefined() {
        if (value instanceof String s) {
            return !s.trim().isEmpty();
        }
        return (value != null);
    }
}