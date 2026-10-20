# Как должна выглядеть модель Пользователя приложения?

## Модель пользователя

Account {

    // --- Идентификация ---
    private UUID id;
    private String email;             // уникальный, для входа
    private String passwordHash;

    // --- Профиль ---
    private String username;          // отображаемое имя / @ник
    private String name;              // опционально
    private String surname;           // опционально
    private LocalDate birthDate;      // опционально
    private String avatarUrl;         // опционально
    private String aboutUser;         // опционально
    private String city;              // важно для поиска

    // --- Контакты ---
    private String phone;             // опционально
    private String tgUsername;        // опционально

    // --- Роль и статус ---
    private AccountStatus status;     // ACTIVE / BLOCKED / DELETED
    private boolean emailVerified;
    private boolean phoneVerified;

    // --- Репутация ---
    private Double participantRating; // как участник
    private Double organizerRating;   // как организатор
    private Integer eventsAttended;   // не уверен, можно в отдельной таблице считать, но нужно будет каждый раз считать
    private Integer eventsOrganized;  // не уверен, можно в отдельной таблице считать, но нужно будет каждый раз считать

    // --- Аудит ---
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- Связи ---
    private Set<Interest> interests;  // Many-to-Many
}

## Отчего отказались и почему?
* Роли не нужны в таблицы, автор мероприятия указан в его таблицы (FK), участники определены также через связующую таблицу
* Отчество не нужно в таблице, избыточно

## Что нужно добавить?
* city - нужно для поиска
* status аккаунта - выключен, заблокирован, удалён
* рейтинг разделить на 2 - как участника и как организатора
* emailVerified - верифицирована ли почта
* phoneVerified - верифицирован ли номер