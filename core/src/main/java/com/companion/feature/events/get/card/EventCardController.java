package com.companion.feature.events.get.card;

import com.companion.common.error.ErrorDetail;
import com.companion.feature.events.get.card.model.EventCardDto;
import com.companion.web.base.AbstractController;
import com.companion.web.model.ApiResponse;
import com.companion.web.special.types.IdDto;
import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventCardController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(EventCardController.class);
    private static final String LOG_PREFIX = "Controller> GET event card";

    @Autowired
    EventCardService eventCardService;

    @PostMapping("/card/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Object>> getEventCard(
            @PathVariable String id,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        log.info("{} start with id: {}", LOG_PREFIX, id);

        //валидация web dto - проверка форматов, типов данных, enum-ов
        IdDto idDto = IdDto.valueOf(id);
        List<ErrorDetail> errors = idDto.validate();

        //если есть ошибки выбрасываем ответ с ошибкой
        if (!errors.isEmpty()) {
            log.error("{} path variable 'id' validation is failed: {}", LOG_PREFIX, errors.toArray());
            return throwError(errors);
        }

        //исполнение юз кейса - внутри происходит вызов метода репозитория с обращением к бд
        var serviceExecute = eventCardService.get(idDto.getId());

        //если есть ошибки возвращаем ответ с ошибкой
        if (serviceExecute.isFailure()) {
            log.error("{} failed with an error", LOG_PREFIX);
            return notFound(serviceExecute.getError());
        }
        var resultBody = serviceExecute.getBody();

        log.info("{} was completed successfully.", LOG_PREFIX);

        System.out.println(resultBody.toString());

        return getRecord(resultBody);
    }
}
