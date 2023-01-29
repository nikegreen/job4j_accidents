# job4j_accidents
Проект "Автонарушители" [#260365]

## 1. Техническое задание - проект "Автонарушители" [#260365]
### 1.1. Описание задания
Требуется разработать сервис "Автонарушители". В сервисе есть 2 типа учётных записей:<br> 
 1. Пользователи.
 2. Автоинспекторы.
#### 1.1.1 Роль "Пользователь"
Пользователь может создать заявление с автонарушением.
Автонарушение содержит:<br>
- адрес, 
- номер машины, 
- описание нарушения 
- фотография нарушения
- статус заявления.<br>
Пользователь не может присвоить и поменять статус заявления.
Если заявление создано, то статус становится - "Принято".   

У заявки есть статус: 
- Принята. 
- Отклонена. 
- Завершена.

#### 1.1.2 Роль "Автоинспектор"
Автоинспектор видит заявки и может поменять статус заявки на:
- Отклонена.
- Завершена.

### 1.2. Внешний вид сервиса.
#### 1.2.1. Главная страница 
Содержит элементы для фильтрации и поиска, список полученных заявлений.
#### 1.2.2. Страница вход на сайт
Содержит:<br>
- Строки для ввода электронной почты и ввода пароля.
- Кнопку 'вход'. 
- Кнопку 'регистрация'.<br>

Кнопка 'вход' - проверяет данные пользователя и если ошибка переходит к окну ошибка, потом снова на окно входа на сайт.
Иначе переходит на главное окно. В главном окне отобразится пользователь.<br>
Кнопку 'регистрация' - переходит на страницу регистрации.

#### 1.2.3. Страница регистрация на сайте
Содержит элементы ввода для:
- имя пользователя
- электронная почта (уникальное зачение в базе сайта)
- пароль
- Кнопку 'вход'.
- Кнопку 'регистрация'.<br>

Кнопка 'вход' - переходит на страницу входа на сайт.<br>
Кнопку 'регистрация' - проверяет данные пользователя и если ошибка переходит к окну ошибка, потом снова на окно входа на сайт.
Иначе сохраняет пользоватнля в базе сайта и переходит на страницу входа на сайт.<br> 

#### 1.2.4. Страница создание заявления
Содержит элементы для ввода:<br>
- адрес,
- номер машины,
- описание нарушения
- фотография нарушения

#### 1.2.5. Страница просмотр заявления
Содержит элементы показывающие:<br>
- адрес,
- номер машины,
- описание нарушения
- фотография нарушения
- статус
#### 1.2.6. Страница с ошибкой
Отбражает сообщение с ошибкой.
 
