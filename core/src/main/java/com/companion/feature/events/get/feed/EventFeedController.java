package com.companion.feature.events.get.feed;


import com.companion.feature.events.get.feed.model.EventFeedRequestBody;
import com.companion.web.base.AbstractController;
import com.companion.common.error.ErrorDetail;
import com.companion.feature.events.get.feed.model.EventFeedSpecificationBody;
import com.companion.web.model.ApiResponse;
import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventFeedController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(EventFeedController.class);
    private static final String LOG_PREFIX = "Controller> GET events /feed";

    @Autowired
    EventFeedService eventFeedService;

    @PostMapping("/feed")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Object>> getEventsFeed(
            @RequestBody EventFeedRequestBody request,
            @AuthenticationPrincipal UserPrincipal currentUser
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
        var specification = EventFeedSpecificationBody.toSpecification(request);

        //исполнение юз кейса - внутри происходит вызов метода репозитория с обращением к бд
        var serviceExecute = eventFeedService.get(specification);

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
