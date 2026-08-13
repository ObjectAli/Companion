package com.companion.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    FIELD_REQUIRED(
            "FIELD_REQUIRED",
            "Поле '%s' обязательно для заполнения",
            "Field '%s' is required",
            "Укажите корректное значение для поля '%s'",
            "Provide a valid value for field '%s'"
    ),

    FIELD_VALUE_NOT_ACCEPTABLE(
            "FIELD_VALUE_NOT_ACCEPTABLE",
            "Поле '%s' может принимать только следующие значения: %s",
            "Field '%s' can be only value: %s",
            "Укажите корректное значение для поля '%s'",
            "Provide a valid value for field '%s'"
    ),

    FIELD_SIZE_OUT_OF_RANGE(
            "FIELD_SIZE_OUT_OF_RANGE",
            "Длина поля '%s' должна быть от %d до %d символов (текущее: %d)",
            "Field '%s' length must be between %d and %d characters (current: %d)",
            "Установите длину поля '%s' в диапазоне [%d, %d]",
            "Set field '%s' length in range [%d, %d]"
    ),

    FIELD_VALUE_OUT_OF_RANGE(
            "FIELD_VALUE_OUT_OF_RANGE",
            "Значение поля '%s' должно быть в диапазоне [%d, %d] (текущее: %d)",
            "Field '%s' value must be in range [%d, %d] (current: %d)",
            "Укажите значение для '%s' в допустимом диапазоне",
            "Provide value for '%s' within allowed range"
    ),

    ENTITY_NOT_FOUND(
            "ENTITY_NOT_FOUND",
            "Объект '%s' с идентификатором '%s' не найден",
            "Entity '%s' with id '%s' not found",
            "Проверьте корректность переданного идентификатора",
            "Verify the provided identifier"
    ),

    DUPLICATE_ENTITY(
            "DUPLICATE_ENTITY",
            "Объект '%s' с полем '%s' = '%s' уже существует",
            "Entity '%s' with field '%s' = '%s' already exists",
            "Измените значение поля '%s' на уникальное",
            "Change field '%s' value to unique one"
    ),

    BUSINESS_RULE_VIOLATION(
            "BUSINESS_RULE_VIOLATION",
            "Нарушено бизнес-правило: %s",
            "Business rule violated: %s",
            "Скорректируйте запрос согласно правилам системы",
            "Adjust request according to system rules"
    );

    private final String code;
    private final String messageRuTemplate;
    private final String messageEnTemplate;
    private final String remediationRuTemplate;
    private final String remediationEnTemplate;

    ErrorCode(String code, String messageRuTemplate, String messageEnTemplate,
              String remediationRuTemplate, String remediationEnTemplate) {
        this.code = code;
        this.messageRuTemplate = messageRuTemplate;
        this.messageEnTemplate = messageEnTemplate;
        this.remediationRuTemplate = remediationRuTemplate;
        this.remediationEnTemplate = remediationEnTemplate;
    }
}
