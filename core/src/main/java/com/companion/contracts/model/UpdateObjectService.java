package com.companion.contracts.model;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public interface UpdateObjectService {

    default Set<String> getUpdatedFields() {
        Set<String> updatedFields = new HashSet<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(this) != null) {
                    updatedFields.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                // логируем ошибку
            }
        }

        return updatedFields;
    }
}
