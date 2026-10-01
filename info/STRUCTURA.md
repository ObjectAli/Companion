# Структура проекта (в работе)
com.companion/
│
├── api/                                    # точка входа
│   └── CompanionStarter
│
├── common/                                 # общие утилиты (без привязки к слоям)
│   ├── result/                             # Result, Success, Failure
│   ├── query/                              # QueryParameters, QueryResult
│   ├── validation/                         # общие валидаторы
│   └── security/                           # AuthContext, CurrentUser (для получения текущего пользователя)
│
├── core/                                   # ядро (контракты, исключения, интерфейсы)
│   ├── exceptions/                         # все исключения
│   │   ├── business/                       # BusinessException, InsufficientFundsException
│   │   ├── technical/                      # DatabaseException, ExternalServiceException
│   │   └── security/                       # AccessDeniedException, UnauthorizedException
│   ├── specifications/                     # интерфейсы спецификаций
│   └── ports/                              # интерфейсы для внешних сервисов (payment, geo)
│       ├── PaymentPort
│       └── GeoPort
│
├── feature/                                # все фичи приложения
│   │
│   ├── events/                             # 🎯 ГЛАВНАЯ ФИЧА: МЕРОПРИЯТИЯ
│   │   ├── model/
│   │   │   ├── Event                       # сущность БД
│   │   │   ├── EventCategory               # enum
│   │   │   ├── EventStatus                 # enum (DRAFT, PUBLISHED, CANCELLED, COMPLETED)
│   │   │   ├── CreateEventRequest          # DTO для создания
│   │   │   ├── UpdateEventRequest          # DTO для обновления
│   │   │   ├── EventResponse               # DTO для ответа
│   │   │   ├── EventDetailsResponse        # DTO с деталями (включая участников)
│   │   │   └── EventSearchRequest          # DTO для поиска с гео
│   │   │
│   │   ├── EventController                 # все эндпоинты /api/v1/events/*
│   │   ├── EventService                    # вся бизнес-логика
│   │   ├── EventRepository                 # все запросы к БД
│   │   │
│   │   ├── organizer/                      # 🔹 Организаторские операции
│   │   │   ├── EventOrganizerController    # POST / PUT / DELETE /my
│   │   │   └── EventOrganizerService       # логика создания/редактирования/отмены
│   │   │
│   │   ├── participant/                    # 🔹 Участнические операции
│   │   │   ├── EventParticipantController  # GET /feed, /{id}, /join, /cancel, /my/upcoming
│   │   │   └── EventParticipantService     # логика записи/отказа/просмотра
│   │   │
│   │   ├── search/                         # 🔹 Поиск с гео
│   │   │   ├── EventSearchController       # GET /search
│   │   │   └── EventSearchService          # логика поиска с гео-фильтрацией
│   │   │
│   │   └── admin/                          # 🔹 Админские операции
│   │       ├── EventAdminController        # GET /admin/stats
│   │       └── EventAdminService           # логика сбора статистики
│   │
│   ├── payments/                           # 💳 ФИЧА: ПЛАТЕЖИ
│   │   ├── model/
│   │   │   ├── Payment                     # сущность платежа
│   │   │   ├── PaymentStatus               # enum (PENDING, PAID, FAILED, REFUNDED)
│   │   │   ├── CreatePaymentRequest        # DTO
│   │   │   ├── PaymentResponse             # DTO для ответа
│   │   │   └── WebhookPayload              # DTO для вебхука
│   │   │
│   │   ├── PaymentController               # POST /create-payment
│   │   ├── PaymentWebhookController        # POST /webhook/yookassa
│   │   ├── PaymentService                  # бизнес-логика платежей
│   │   ├── PaymentRepository               # запросы к БД
│   │   └── PaymentValidator                # валидация платежей
│   │
│   ├── payouts/                            # 💸 ФИЧА: ВЫПЛАТЫ ОРГАНИЗАТОРАМ
│   │   ├── model/
│   │   │   ├── Payout                      # сущность выплаты
│   │   │   └── PayoutStatus                # enum
│   │   │
│   │   ├── PayoutController                # POST /admin/payouts/run (админский эндпоинт)
│   │   ├── PayoutService                   # логика выплат
│   │   └── PayoutRepository                # запросы к БД
│   │
│   └── stats/                              # 📊 ФИЧА: СТАТИСТИКА (если отдельно от админки)
│       ├── model/
│       │   ├── StatsResponse
│       │   └── StatsPeriod
│       ├── StatsController                 # GET /admin/stats (может быть здесь)
│       └── StatsService                    # сбор статистики
│
├── web/                                    # Web-слой (общие компоненты)
│   ├── base/                               # AbstractController, BaseResponse
│   ├── dto/                                # общие DTO
│   │   ├── request/
│   │   │   ├── DisplayRecordsRequest
│   │   │   ├── FieldSimple
│   │   │   └── GeoLocationRequest
│   │   └── response/
│   │       ├── ApiResponse
│   │       ├── ErrorCode
│   │       └── ErrorDetail
│   ├── validation/                         # валидаторы DTO
│   │   ├── RequestBodyValidator
│   │   └── DatabaseQueryValidator
│   └── security/                           # безопасность на Web-уровне
│       ├── JwtAuthenticationFilter
│       └── SecurityConfig
│
└── infrastructure/                         # инфраструктура (внешние интеграции)
├── payment/                            # интеграция с ЮKassa
│   ├── YooKassaClient
│   ├── YooKassaConfig
│   └── PaymentPortImpl                  # реализация PaymentPort
├── geo/                                 # интеграция с гео-сервисом
│   ├── GeoClient
│   └── GeoPortImpl                      # реализация GeoPort
└── persistence/                         # общие настройки БД
├── JooqConfig
└── DatabaseInitializer