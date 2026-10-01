package com.companion.feature.events.get.feed.model;

import com.companion.common.query.QueryParameters;
import com.companion.contracts.validators.DatabaseQueryValidator;
import com.companion.common.error.ErrorDetail;
import com.companion.jooq.generated.tables.Events;
import org.jooq.Condition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EventFeedQueryRepositoryBody extends QueryParameters implements DatabaseQueryValidator {

    public EventFeedQueryRepositoryBody(Builder builder) {
        super(builder);
    }

    public static QueryParameters toDatabaseQueryParameters(EventFeedSpecificationBody specification) {

        return new QueryParameters.Builder()
                .conditions(prepareConditions(specification))
                .sortBy((specification.getSortBy()))
                .sortDirection(specification.getSortDirection())
                .offset(specification.getSortBy())
                .limit(specification.getSize())
                .build();
    }

    private static List<Condition> prepareConditions(EventFeedSpecificationBody specification) {
        List<Condition> conditions = new ArrayList<>();

        if(specification.getCategory() != null) {
            Condition condition = Events.EVENTS.CATEGORY.in(specification.getCategory().getValue());
            condition.and(condition);
        }

        return conditions;
    }

    @Override
    public List<ErrorDetail> validate() {
        return Collections.emptyList();
    }
}
