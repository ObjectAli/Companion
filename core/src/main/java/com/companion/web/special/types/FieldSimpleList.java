package com.companion.web.special.types;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class FieldSimpleList<O> {

    List<O> value;

    public FieldSimpleList(List<O> list) {
        if (list != null) {
            this.value = list;
        }
    }
}