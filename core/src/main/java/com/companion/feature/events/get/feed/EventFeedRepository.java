package com.companion.feature.events.get.feed;


import com.companion.common.result.Result;
import com.companion.common.query.QueryParameters;
import com.companion.common.query.QueryResult;
import com.companion.common.error.ErrorDetail;
import com.companion.jooq.generated.tables.pojos.Events;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.companion.jooq.generated.tables.Events.EVENTS;

@Repository
public class EventFeedRepository {

    private final DSLContext dslContext;

    public EventFeedRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private static final Logger log = LoggerFactory.getLogger(EventFeedRepository.class);
    private static final String LOG_PREFIX = "Repository> GET events";

    public Result<QueryResult<Events>, List<ErrorDetail>> find(QueryParameters queryParameters) {
        log.info("{} start with query parameters: {}", LOG_PREFIX, queryParameters);

        List<ErrorDetail> errors = queryParameters.validate();
        if (!errors.isEmpty()) {
            log.error("{} query parameters validation is failed: {}", LOG_PREFIX, errors.toArray());

            return Result.failure(errors);
        }

        var records = dslContext
                .select()
                .from(EVENTS)
                .where(queryParameters.getConditions())
//              .orderBy(queryParameters.getSortBy())
                .limit(queryParameters.getLimit());

        Integer totalCount = dslContext.selectCount()
                .from(records)
                .fetchOneInto(Integer.class);

        List<Events> eventsList = dslContext.select()
                .from(records)
                .fetchInto(Events.class);

        log.info("{} was completed successfully. {}/{} records found.", LOG_PREFIX, eventsList.size(), totalCount);

        return Result.success(new QueryResult<>(eventsList, eventsList.size(), totalCount, queryParameters.getOffset(), queryParameters.getSortBy(), queryParameters.getSortDirection()));
    }
}
