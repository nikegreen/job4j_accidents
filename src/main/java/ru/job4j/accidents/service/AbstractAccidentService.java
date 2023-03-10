package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;
import java.util.List;
import java.util.Optional;

/**
 * @author nikez
 * @version $Id: $Id
 * Интерфейс сервис для происшествий (Accident)
 */
public interface AbstractAccidentService {
    /**
     * Функция добавляет в хранилище новое происшествие. Затирает id новым значением.
     * Нет проверки на дубликаты.
     * @param accident - происшествие тип {@link ru.job4j.accidents.model.Accident}
     * @return результат добавления тип {@link boolean}
     * true  - добавлен
     * false - ошибка
     */
    boolean add(Accident accident);

    /**
     * Удаляет происшествие из хранилища по {@param id} тип {@link int}.
     * @param id - идентификатор положительное число больше 0.
     * @return результат удаления тип {@link boolean}
     * true  - успешно удалён.
     * false - ошибка или нет в хранилище.
     */
    boolean delete(int id);

    /**
     * Вернуть список всех происшествий
     * @return список типа {@link java.util.List<ru.job4j.accidents.model.Accident>}
     */
    List<Accident> findAll();

    /**
     * Найти происшествие по {@param id}.
     * @param id id - идентификатор положительное число больше 0.
     * @return происшествие тип {@link java.util.Optional<ru.job4j.accidents.model.Accident>}
     * если не найдено, то результат Optional.Empty
     */
    Optional<Accident> findById(int id);

    /**
     * Сохраняет значение из {@param accident} в хранилище.
     * @param accident - происшествие с новыми полями (кроме идентификатора происшествия)
     * @return результат обновления  тип boolean.
     * true  - обновлено в хранилище.
     * false - ошибка обновления. Может отсутствовать id.
     */
    boolean update(Accident accident);

    /**
     * Внутренняя функция для установки поля rules класса {@link ru.job4j.accidents.model.Accident}
     * @param accident тип {@link ru.job4j.accidents.model.Accident} содержит нарушение,
     *                 в котором нужно заполнить поле rules из {@param ids}.
     * @param ids массив типа {@link java.lang.String}[]. Массив строк с идентификаторами получаем
     *            из страницы от элемента тэг - select в режиме множественного выбора, имя "rIds".
     * @return тип boolean. Вернёт результат выполнения функции.
     * true  - поле заполнено.
     * false - не заполнено, ошибка.
     */
    boolean setRules(Accident accident, String[] ids);
}
