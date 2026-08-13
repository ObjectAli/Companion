package com.companion.web.special.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FieldWithExact {

    String value;

    boolean exact;

    boolean caseSensitive;

    public boolean isDefined() {
        return (value != null && !value.trim().isEmpty());
    }
}
