package com.companion.feature.account.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Builder
public class AccountDto {

    private UUID id;
    private String username;
    private String surname;
    private String name;
    private String patronymic;
    private String aboutUser;
    private String passwordHash;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String tgUsername;
    private String avatarUrl;
    private BigDecimal rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AccountDto toAccountDto(CreateAccountDto createAccountDto) {
        return AccountDto.builder()
                .id(UUID.randomUUID())
                .username(createAccountDto.getUsername())
                .surname(createAccountDto.getSurname())
                .name(createAccountDto.getName())
                .patronymic(createAccountDto.getPatronymic())
                .aboutUser(createAccountDto.getAboutUser())
                .passwordHash(createAccountDto.getPasswordHash())
                .birthDate(createAccountDto.getBirthDate())
                .phone(createAccountDto.getPhone())
                .email(createAccountDto.getEmail())
                .tgUsername(createAccountDto.getTgUsername())
                .avatarUrl(createAccountDto.getAvatarUrl())
                .rating(createAccountDto.getRating())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountDto toAccountDto(UpdateAccountDto updateAccountDto) {
        return AccountDto.builder()
                .id(updateAccountDto.getId())
                .username(updateAccountDto.getUsername())
                .surname(updateAccountDto.getSurname())
                .name(updateAccountDto.getName())
                .patronymic(updateAccountDto.getPatronymic())
                .aboutUser(updateAccountDto.getAboutUser())
                .passwordHash(updateAccountDto.getPasswordHash())
                .birthDate(updateAccountDto.getBirthDate())
                .phone(updateAccountDto.getPhone())
                .email(updateAccountDto.getEmail())
                .tgUsername(updateAccountDto.getTgUsername())
                .avatarUrl(updateAccountDto.getAvatarUrl())
                .rating(updateAccountDto.getRating())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
