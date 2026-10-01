# Ключевые API эндпоинты (REST):

## Мероприятия
### Управление (Организатор мероприятия)
* POST   /api/v1/events                    -- создание мероприятия       +
* PUT    /api/v1/events/{id}               -- редактирование             +
* DELETE /api/v1/events/{id}               -- отмена (с возвратом денег) +

### Подписка и отписка от мероприятия
* POST   /api/v1/events/{id}/join          -- записаться (создаёт платёж)
* POST   /api/v1/events/{id}/cancel        -- отказаться (возврат, если до события > N часов)

### Просмотр мероприятий
* GET    /api/v1/events/feed               -- лента (фильтр: дата, категория, радиус) +...
* GET    /api/v1/events/{id}               -- детали мероприятия
* GET    /api/v1/events/my                 -- мероприятия, которые я организовываю
* GET    /api/v1/events/upcoming           -- мероприятия, на который я подписан

## Платежи
* POST   /api/v1/payments/create-payment   -- создание платежа (ЮKassa)
* POST   /api/v1/payments/webhook/yookassa -- вебхук от ЮKassa (обновление статуса)

## Поиск (с гео)
* GET    /api/v1/events/search?lat=55.75&lon=37.62&radius=5&date=2026-06-10

## Админ
* GET    /api/v1/admin/stats               -- обороты, комиссии, количество мероприятий
* POST   /api/v1/admin/payouts/run         -- запуск выплат организаторам

## Аккаунт
* POST   /api/v1/account/{id}              -- создание               +
* PUT    /api/v1/account/{id}              -- редактирование         + 
* DELETE /api/v1/account/{id}              -- удаление               +

## Аутентификация
* POST   /api/v1/auth/login                -- вход                   +
* POST   /api/v1/auth/logout               -- выход                  +