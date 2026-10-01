package com.companion.feature.account.model;

import com.companion.common.error.ErrorDetail;
import com.companion.common.validation.ValidationUtil;
import com.companion.contracts.validators.RequestBodyValidator;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class CreateAccountDto implements RequestBodyValidator {

    private String username;       //псевдоним
    private String surname;        //фамилия
    private String name;           //имя
    private String patronymic;     //отчество
    private String aboutUser;      //дополнительная информация
    private String passwordHash;   //хеш пароля
    private LocalDate birthDate;   //день рождения
    private String phone;          //телефон
    private String email;          //email
    private String tgUsername;     //псевдоним в Телеграм
    private String avatarUrl;      //фото профиля
    private BigDecimal rating;     //рейтинг

    @Override
    public List<ErrorDetail> validate() {
        return new ValidationProcessor().execute();
    }

    private class ValidationProcessor {
        private void validateUsername(List<ErrorDetail> errorDetails) {
            final String attributeName = "username";
            ValidationUtil.fieldIsNotNull(errorDetails, username, attributeName);
            ValidationUtil.stringIsNotEmpty(errorDetails, username, attributeName);
            ValidationUtil.stringLength(errorDetails, username, attributeName, 5, 10);
        }

        private void validateSurname(List<ErrorDetail> errorDetails) {
            final String attributeName = "surname";
            ValidationUtil.fieldIsNotNull(errorDetails, surname, attributeName);
            ValidationUtil.stringIsNotEmpty(errorDetails, surname, attributeName);
            ValidationUtil.stringLength(errorDetails, surname, attributeName, 2, 20);
        }

        private void validateName(List<ErrorDetail> errorDetails) {
            final String attributeName = "name";
            ValidationUtil.fieldIsNotNull(errorDetails, name, attributeName);
            ValidationUtil.stringIsNotEmpty(errorDetails, name, attributeName);
            ValidationUtil.stringLength(errorDetails, name, attributeName, 2, 10);
        }

        public List<ErrorDetail> execute(){
            List<ErrorDetail> errorsList = new ArrayList<>();

            validateUsername(errorsList);
            validateSurname(errorsList);
            validateName(errorsList);

            return errorsList;
        }
    }
}
