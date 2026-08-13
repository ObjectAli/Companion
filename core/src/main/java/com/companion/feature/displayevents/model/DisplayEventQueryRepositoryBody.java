package com.companion.feature.displayevents.model;

import com.companion.common.query.QueryParameters;
import com.companion.contracts.validators.DatabaseQueryValidator;
import com.companion.common.error.ErrorDetail;
import com.companion.jooq.generated.tables.Events;
import org.jooq.Condition;

import java.util.ArrayList;
import java.util.List;


public class DisplayEventQueryRepositoryBody extends QueryParameters implements DatabaseQueryValidator {

    public DisplayEventQueryRepositoryBody(Builder builder) {
        super(builder);
    }

    public static QueryParameters toDatabaseQueryParameters(DisplayEventsSpecificationBody specification) {

        return new QueryParameters.Builder()
                .conditions(prepareConditions(specification))
                .sortBy((specification.getSortBy()))
                .sortDirection(specification.getSortDirection())
                .offset(specification.getSortBy())
                .limit(specification.getSize())
                .build();
    }

    private static List<Condition> prepareConditions(DisplayEventsSpecificationBody specification) {
        List<Condition> conditions = new ArrayList<>();

        if(specification.getCategory() != null) {
            Condition condition = Events.EVENTS.CATEGORY.in(specification.getCategory().getValue());
            condition.and(condition);
        }

        return conditions;
    }

    @Override
    public List<ErrorDetail> validate() {
        return null;
    }
}
