package com.companion.feature.account.model;

import com.companion.common.error.ErrorDetail;
import com.companion.common.validation.ValidationUtil;
import com.companion.contracts.validators.RequestBodyValidator;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class CreateAccountDto implements RequestBodyValidator {

    private String username;       //псевдоним
    private String email;          //email
    private String password;       //пароль не в зашифрованном виде

    @Override
    public List<ErrorDetail> validate() {
        return new ValidationProcessor().execute();
    }

    private class ValidationProcessor {
        private void validateUsername(List<ErrorDetail> errorDetails) {
            final String attributeName = "username";
            ValidationUtil.fieldIsNotNull(errorDetails, username, attributeName);
            ValidationUtil.stringIsNotEmpty(errorDetails, username, attributeName);
            ValidationUtil.stringLength(errorDetails, username, attributeName, 6, 32);
        }

        private void validateEmail(List<ErrorDetail> errorDetails) {
            final String attributeName = "email";
            ValidationUtil.fieldIsNotNull(errorDetails, email, attributeName);
            ValidationUtil.stringIsNotEmpty(errorDetails, email, attributeName);
            ValidationUtil.stringLength(errorDetails, email, attributeName, 6, 32);
        }

        private void validatePassword(List<ErrorDetail> errorDetails) {
            final String attributeName = "password";
            ValidationUtil.fieldIsNotNull(errorDetails, password, attributeName);
            ValidationUtil.stringIsNotEmpty(errorDetails, password, attributeName);
            ValidationUtil.stringLength(errorDetails, password, attributeName, 6, 64);
        }

        public List<ErrorDetail> execute() {
            List<ErrorDetail> errorsList = new ArrayList<>();

            validateUsername(errorsList);
            validateEmail(errorsList);
            validatePassword(errorsList);

            return errorsList;
        }
    }
}
