package com.companion.feature.auth.model;

import com.companion.common.error.ErrorDetail;
import com.companion.contracts.validators.RequestBodyValidator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginRequestBody implements RequestBodyValidator {
    private String username;
    private String password;

    @Override
    public List<ErrorDetail> validate() {
        return null;
    }
}