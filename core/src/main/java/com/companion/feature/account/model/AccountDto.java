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
    private String email;
    private String passwordHash;
    private String username;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String avatarUrl;
    private String aboutUser;
    private String city;
    private String phone;
    private String tgUsername;
    private Object status;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private Double participantRating;
    private Double organizerRating;
    private Integer eventsAttended;
    private Integer eventsOrganized;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AccountDto toAccountDto(CreateAccountDto createAccountDto, String encodedPassword) {
        return AccountDto.builder()
                .id(UUID.randomUUID())
                .username(createAccountDto.getUsername())
                .email(createAccountDto.getEmail())
                .passwordHash(encodedPassword)
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();
    }

    //TODO дозаполнить
    public static AccountDto toAccountDto(UpdateAccountDto updateAccountDto) {
        return AccountDto.builder()
                .id(updateAccountDto.getId())
                .username(updateAccountDto.getUsername())
                .surname(updateAccountDto.getSurname())
                .name(updateAccountDto.getName())
                .aboutUser(updateAccountDto.getAboutUser())
                .passwordHash(updateAccountDto.getPassword())
                .birthDate(updateAccountDto.getBirthDate())
                .phone(updateAccountDto.getPhone())
                .email(updateAccountDto.getEmail())
                .tgUsername(updateAccountDto.getTgUsername())
                .avatarUrl(updateAccountDto.getAvatarUrl())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
