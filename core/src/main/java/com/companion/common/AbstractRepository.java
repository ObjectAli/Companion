package com.companion.common;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;

public class AbstractRepository {

    private static final Logger log = LoggerFactory.getLogger(AbstractRepository.class);

    protected final DSLContext dslContext;

    public AbstractRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    protected static boolean setChangedForUpdatedFieldsOnly(Record newRecord, Record originalRecord,
                                                         Set<String> updateOnlyFieldsNames) {
        log.trace("Processing fields {} as present", updateOnlyFieldsNames);

        boolean recordHasChanges = false;

        if (updateOnlyFieldsNames != null && !updateOnlyFieldsNames.isEmpty()) {
            Field<?>[] fields = newRecord.fields();
            for (Field<?> field : fields) {

                boolean fieldWasChanged = false;
                if (updateOnlyFieldsNames.contains(field.getName()) && (!Objects.equals(newRecord.get(field.getName()), originalRecord.get(field.getName())))) {
                        fieldWasChanged = true;
                        recordHasChanges = true;
                }
                newRecord.changed(field, fieldWasChanged);
                log.trace("Processing field {} as changed = {} ", field.getName(), fieldWasChanged);
            }
        }

        return recordHasChanges;
    }
}
