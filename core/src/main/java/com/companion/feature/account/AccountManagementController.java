package com.companion.feature.account;

import com.companion.common.error.ErrorDetail;
import com.companion.feature.account.model.CreateAccountDto;
import com.companion.feature.account.model.UpdateAccountDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * POST   /api/v1/accounts                    -- создание аккаунта
 * PUT    /api/v1/accounts/{id}               -- редактирование аккаунта
 * DELETE /api/v1/accounts/{id}               -- удаление аккаунта
 */
@RestController
@RequestMapping("/accounts")
public class AccountManagementController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AccountManagementController.class);

    @Autowired
    AccountManagementService accountManagementService;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createAccount(
            @RequestBody CreateAccountDto requestBody
    ) {
        log.info("Start create account with username: {}", requestBody.getUsername());

//        requestBody.setPasswordHash(passwordEncoder.encode(requestBody.getPasswordHash()));

        //валидация web dto - проверка форматов, типов данных, enum-ов
        List<ErrorDetail> errors = requestBody.validate();

        //если есть ошибки выбрасываем ответ с ошибкой
        if (!errors.isEmpty()) {
            log.error("{} request body validation is failed: {}", errors.toArray());
            return throwError(errors);
        }

        var result = accountManagementService.create(requestBody, null);

        log.info("Create account with id {} and username {} was completed successfully.", result.getBody().getId(), result.getBody().getUsername());

        return ok(result.getBody(), 201);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Object>> updateAccount(
            UpdateAccountDto requestBody,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        log.info("Start update account with body: {}", requestBody);

        //валидация web dto - проверка форматов, типов данных, enum-ов
        List<ErrorDetail> errors = requestBody.validate();
        if (!errors.isEmpty()) {
            log.error("{} request body validation is failed: {}", errors.toArray());
            return throwError(errors);
        }

        UUID organizerId = userDetailsService.loadUserByUsername(currentUser.getName()).getUUID();

        var result = accountManagementService.update(requestBody, organizerId);

        log.info("Update account with id {} and username {} was completed successfully", result.getBody().getId(), result.getBody().getUsername());

        return ok(result.getBody(), 200);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Object>> deleteAccount(
            String id,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        log.info("Start delete account with id {}", id);

        UUID organizerId = userDetailsService.loadUserByUsername(currentUser.getName()).getUUID();

        var result = accountManagementService.delete(UUID.fromString(id), organizerId);

        log.info("Delete account with id {} was completed successfully.", id);

        return ok(result.getBody(), 200);
    }
}
