# Структура проекта (в работе)
# Полная структура проекта с учетом всех слоев
my-application/
├── pom.xml (parent POM)
│
├── core/                                    # ЯДРО (независимый слой)
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│               └── com/
│                   └── api/
│                       └── core/
│                           ├── domain/                    # Доменный слой
│                           │   ├── model/
│                           │   │   ├── Event.java
│                           │   │   └── User.java
│                           │   ├── valueobjects/
│                           │   │   ├── GeoPoint.java
│                           │   │   ├── Money.java
│                           │   │   └── EventStatus.java
│                           │   ├── specification/         # СПЕЦИФИКАЦИИ ДЛЯ ФИЛЬТРОВ
│                           │   │   ├── EventSpecification.java
│                           │   │   └── GenericSpecification.java
│                           │   └── exception/
│                           │       ├── DomainException.java
│                           │       └── EventFullException.java
│                           │
│                           ├── application/               # ПРИЛОЖЕНИЕ (Use Cases)
│                           │   ├── service/              # Сервисы приложения
│                           │   │   ├── EventService.java
│                           │   │   ├── UserService.java
│                           │   │   └── AuthService.java
│                           │   ├── dto/                  # DTO для Use Cases
│                           │   │   ├── request/          # ВХОДНЫЕ DTO
│                           │   │   │   ├── EventsSearchRequest.java
│                           │   │   │   ├── CreateEventCommand.java
│                           │   │   │   └── JoinEventCommand.java
│                           │   │   └── response/         # ВЫХОДНЫЕ DTO
│                           │   │       ├── EventResponse.java
│                           │   │       └── PagedResponse.java
│                           │   └── mapper/               # Мапперы Domain ↔ DTO
│                           │       └── EventMapper.java
│                           │
│                           └── port/                      # ПОРТЫ (интерфейсы)
│                               ├── repository/           # Интерфейсы репозиториев
│                               │   ├── EventRepository.java
│                               │   └── UserRepository.java
│                               ├── security/             # Интерфейсы безопасности
│                               │   ├── AuthenticationPort.java
│                               │   └── AuthorizationPort.java
│                               └── external/             # Внешние сервисы
│                                   └── NotificationPort.java
│
├── infrastructure/                          # ИНФРАСТРУКТУРА
│   ├── database-module/                    # МОДУЛЬ БД
│   │   ├── pom.xml
│   │   └── src/
│   │       └── main/
│   │           ├── java/
│   │           │   └── com/
│   │           │       └── api/
│   │           │           └── infrastructure/
│   │           │               └── database/
│   │           │                   ├── repository/       # РЕАЛИЗАЦИЯ РЕПОЗИТОРИЕВ
│   │           │                   │   ├── jooq/        # JOOQ реализация
│   │           │                   │   │   ├── JooqEventRepository.java
│   │           │                   │   │   └── JooqUserRepository.java
│   │           │                   │   └── jpa/         # JPA реализация (альтернатива)
│   │           │                   │       ├── JpaEventRepository.java
│   │           │                   │       └── JpaUserRepository.java
│   │           │                   ├── specification/    # БИЛДЕРЫ СПЕЦИФИКАЦИЙ
│   │           │                   │   └── JooqSpecificationBuilder.java
│   │           │                   ├── entity/          # JPA Entity
│   │           │                   │   ├── EventEntity.java
│   │           │                   │   └── UserEntity.java
│   │           │                   ├── converter/       # КОНВЕРТЕРЫ
│   │           │                   │   ├── EventRecordConverter.java
│   │           │                   │   └── GeoPointConverter.java
│   │           │                   └── config/          # КОНФИГУРАЦИЯ БД
│   │           │                       ├── DatabaseConfig.java
│   │           │                       └── JooqConfig.java
│   │           └── resources/
│   │               └── db/
│   │                   └── migration/           # Flyway миграции
│   │                       ├── V1__create_events_table.sql
│   │                       └── V2__create_users_table.sql
│   │
│   ├── security-module/                    # МОДУЛЬ БЕЗОПАСНОСТИ
│   │   ├── pom.xml
│   │   └── src/
│   │       └── main/
│   │           └── java/
│   │               └── com/
│   │                   └── api/
│   │                       └── infrastructure/
│   │                           └── security/
│   │                               ├── config/          # КОНФИГУРАЦИЯ SECURITY
│   │                               │   ├── SecurityConfig.java
│   │                               │   └── CorsConfig.java
│   │                               ├── filter/          # JWT ФИЛЬТРЫ
│   │                               │   └── JwtAuthenticationFilter.java
│   │                               ├── jwt/             # JWT УТИЛИТЫ
│   │                               │   ├── JwtService.java
│   │                               │   └── JwtProperties.java
│   │                               ├── service/         # СЕРВИСЫ БЕЗОПАСНОСТИ
│   │                               │   ├── CustomUserDetailsService.java
│   │                               │   └── AuthenticationService.java
│   │                               ├── adapter/         # АДАПТЕРЫ ПОРТОВ
│   │                               │   └── SecurityPortAdapter.java
│   │                               └── dto/             # DTO ДЛЯ АУТЕНТИФИКАЦИИ
│   │                                   ├── AuthRequest.java
│   │                                   └── AuthResponse.java
│   │
│   └── web-module/                       # ВЕБ МОДУЛЬ
│       ├── pom.xml
│       └── src/
│           └── main/
│               ├── java/
│               │   └── com/
│               │       └── api/
│               │           └── infrastructure/
│               │               └── web/
│               │                   ├── controller/      # REST КОНТРОЛЛЕРЫ
│               │                   │   ├── DisplayEventController.java
│               │                   │   ├── AuthController.java
│               │                   │   └── UserController.java
│               │                   ├── dto/             # WEB DTO
│               │                   │   ├── request/    # ВХОДНЫЕ DTO
│               │                   │   │   ├── EventsSearchRequest.java
│               │                   │   │   ├── EventCreateRequest.java
│               │                   │   │   └── AuthRequest.java
│               │                   │   └── response/   # ВЫХОДНЫЕ DTO
│               │                   │       ├── DisplayEventsResponse.java
│               │                   │       └── AuthResponse.java
│               │                   ├── mapper/         # MAPPER ДЛЯ WEB
│               │                   │   └── EventWebMapper.java
│               │                   ├── exception/      # ОБРАБОТКА ОШИБОК
│               │                   │   ├── GlobalExceptionHandler.java
│               │                   │   ├── ApiError.java
│               │                   │   └── ValidationError.java
│               │                   └── config/         # WEB КОНФИГУРАЦИИ
│               │                       ├── WebConfig.java
│               │                       └── OpenApiConfig.java
│               └── resources/
│                   └── swagger/
│                       └── openapi.yaml
│
└── application-module/                    # СБОРКА ПРИЛОЖЕНИЯ
├── pom.xml
└── src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── api/
│   │           └── Application.java        # MAIN CLASS
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       └── logback-spring.xml
└── test/
└── java/
└── com/
└── api/
└── integration/            # ИНТЕГРАЦИОННЫЕ ТЕСТЫ
├── EventControllerTest.java
└── EventServiceTest.java



## Расширенная структура с дополнительными модулями (опционально)
my-application/
├── core/                                    # Ядро (обязательно)
├── infrastructure/                          # Инфраструктура (обязательно)
│   ├── database-module/                    # БД
│   ├── security-module/                    # Безопасность
│   └── web-module/                         # Веб
│
├── api-client/                             # КЛИЕНТ ДЛЯ ДРУГИХ СЕРВИСОВ (опционально)
│   ├── pom.xml
│   └── src/main/java/com/api/client/
│       ├── dto/
│       ├── service/
│       └── config/
│
├── messaging/                              # ОБРАБОТКА СООБЩЕНИЙ (опционально)
│   ├── pom.xml
│   └── src/main/java/com/api/messaging/
│       ├── consumer/
│       ├── producer/
│       └── config/
│
├── scheduler/                              # ПЛАНИРОВЩИК ЗАДАЧ (опционально)
│   ├── pom.xml
│   └── src/main/java/com/api/scheduler/
│       ├── jobs/
│       └── config/
│
└── monitoring/                             # МОНИТОРИНГ (опционально)
├── pom.xml
└── src/main/java/com/api/monitoring/
├── metrics/
├── health/
└── config/