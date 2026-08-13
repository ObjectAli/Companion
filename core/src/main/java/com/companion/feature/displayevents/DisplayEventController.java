package com.companion.feature.displayevents;


import com.companion.web.base.AbstractController;
import com.companion.common.error.ErrorDetail;
import com.companion.feature.displayevents.model.DisplayEventsRequestBody;
import com.companion.feature.displayevents.model.DisplayEventsSpecificationBody;
import com.companion.web.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DisplayEventController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(DisplayEventController.class);
    private static final String LOG_PREFIX = "Controller> GET events /feed";

    @Autowired
    DisplayEventsService displayEventsService;


    @GetMapping("/feed")
    public ResponseEntity<ApiResponse<Object>> getEventsFeed(
            DisplayEventsRequestBody request
    ) {
        log.info("{} start with request body: {}", LOG_PREFIX, request);

        //валидация web dto - проверка форматов, типов данных, enum-ов
        List<ErrorDetail> errors = request.validate();

        //если есть ошибки выбрасываем ответ с ошибкой
        if (!errors.isEmpty()) {
            log.error("{} request body validation is failed: {}", LOG_PREFIX, errors.toArray());
            return throwError(errors);
        }

        //валидация требований к use case - проверка логическая
        var specification = DisplayEventsSpecificationBody.toSpecification(request);

        //исполнение юз кейса - внутри происходит вызов метода репозитория с обращением к бд
        var serviceExecute = displayEventsService.execute(specification);

        //если есть ошибки возвращаем ответ с ошибкой
        if (serviceExecute.isFailure()) {
            log.error("{} failed with an error", LOG_PREFIX);
            return throwError(serviceExecute.getError());
        }
        var resultBody = serviceExecute.getBody();

        log.info("{} was completed successfully. {}/{} records return.", LOG_PREFIX, resultBody.getCount(), resultBody.getTotalCount());
        return readRecords(resultBody);
    }
}
