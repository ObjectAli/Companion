package com.companion.feature.events.management;

import com.companion.common.error.ErrorDetail;
import com.companion.feature.events.management.model.CreateEventDto;
import com.companion.feature.events.management.model.UpdateEventDto;
import com.companion.security.service.CustomUserDetailsService;
import com.companion.web.base.AbstractController;
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
import java.util.UUID;

/**
 * POST   /api/v1/events                    -- создание мероприятия
 * PUT    /api/v1/events/{id}               -- редактирование мероприятия
 * DELETE /api/v1/events/{id}               -- отмена (с возвратом денег) мероприятия
 */
@RestController
@RequestMapping("/events")
public class EventManagementController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(EventManagementController.class);

    @Autowired
    private EventManagementService eventManagementService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Object>> createEvent(
            @RequestBody CreateEventDto requestBody,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        log.info("Start create event with body: {}", requestBody);

        //валидация web dto - проверка форматов, типов данных, enum-ов
        List<ErrorDetail> errors = requestBody.validate();

        //если есть ошибки выбрасываем ответ с ошибкой
        if (!errors.isEmpty()) {
            log.error("{} request body validation is failed: {}", errors.toArray());
            return throwError(errors);
        }

        UUID organizerId = userDetailsService.loadUserByUsername(currentUser.getName()).getUUID();

        var result = eventManagementService.create(requestBody, organizerId);

        log.info("Create event with id {} was completed successfully. Result: {}", result.getBody().getId(), requestBody);

        return ok(result.getBody(), 201);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Object>> updateEvent(
            UpdateEventDto requestBody,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        log.info("Start update event with body: {}", requestBody);

        //валидация web dto - проверка форматов, типов данных, enum-ов
        List<ErrorDetail> errors = requestBody.validate();
        if (!errors.isEmpty()) {
            log.error("{} request body validation is failed: {}", errors.toArray());
            return throwError(errors);
        }

        UUID organizerId = userDetailsService.loadUserByUsername(currentUser.getName()).getUUID();

        var result = eventManagementService.update(requestBody, organizerId);

        log.info("Update event with id {} was completed successfully. Result: {}", result.getBody().getId(), requestBody);

        return ok(result.getBody(), 200);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Object>> deleteEvent(
            String id,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        log.info("Start delete event with id {}", id);

        UUID organizerId = userDetailsService.loadUserByUsername(currentUser.getName()).getUUID();

        var result = eventManagementService.delete(UUID.fromString(id), organizerId);

        log.info("Delete event with id {} was completed successfully.", id);

        return ok(result.getBody(), 200);
    }
}
