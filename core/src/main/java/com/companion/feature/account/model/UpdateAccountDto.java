package com.companion.feature.account.model;

import com.companion.contracts.model.UpdateObjectService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Getter
@EqualsAndHashCode(callSuper = false)
public class UpdateAccountDto extends CreateAccountDto implements UpdateObjectService {

    private UUID id;
    private String surname;        //фамилия
    private String name;           //имя
    private String patronymic;     //отчество
    private String aboutUser;      //дополнительная информация
    private String passwordHash;   //хеш пароля
    private LocalDate birthDate;   //день рождения
    private String phone;          //телефон
    private String tgUsername;     //псевдоним в Телеграм
    private String avatarUrl;      //фото профиля
    private BigDecimal rating;     //рейтинг
}
