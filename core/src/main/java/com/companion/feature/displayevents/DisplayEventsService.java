package com.companion.feature.displayevents;

import com.companion.common.error.ErrorDetail;
import com.companion.common.query.QueryResult;
import com.companion.common.result.Result;
import com.companion.contracts.services.DisplayObjectsService;
import com.companion.feature.displayevents.model.DisplayEventQueryRepositoryBody;
import com.companion.feature.displayevents.model.DisplayEventsSpecificationBody;
import com.companion.jooq.generated.tables.pojos.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisplayEventsService implements DisplayObjectsService<Events, DisplayEventsSpecificationBody> {

    private static final Logger log = LoggerFactory.getLogger(DisplayEventsService.class);
    private static final String LOG_PREFIX = "Service> GET events";


    private final DisplayEventRepository displayEventRepository;

    public DisplayEventsService(DisplayEventRepository displayEventRepository) {
        this.displayEventRepository = displayEventRepository;
    }

    @Override
    public Result<QueryResult<Events>, List<ErrorDetail>> execute(DisplayEventsSpecificationBody querySpecification) {

        log.info("{} start with specification: {}", LOG_PREFIX, querySpecification);

        List<ErrorDetail> errors = querySpecification.validate();
        if (!errors.isEmpty()) {
            log.info("{} specification validation is failed: {}", LOG_PREFIX, errors.toArray());
            return Result.failure(errors);
        }

        //преобразование спецификации в параметры запроса к БД
        var queryParameters = DisplayEventQueryRepositoryBody.toDatabaseQueryParameters(querySpecification);

        //осуществление выборки
        Result<QueryResult<Events>, List<ErrorDetail>> queryResult = displayEventRepository.find(queryParameters);

        log.info("{} was completed, successfully - {}", LOG_PREFIX, queryResult.isSuccess());

        return queryResult;
    }
}
