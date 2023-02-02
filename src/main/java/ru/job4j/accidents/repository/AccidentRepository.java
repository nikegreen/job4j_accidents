package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * интерфейс хранилища для происшествий (Accident)
 */
public interface AccidentRepository {
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
     * @param  id - идентификатор положительное число больше 0.
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
     * Найти тип происшествия по id. Емли нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    Optional<AccidentType> findTypeById(int id);

    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    List<AccidentType> findTypeAll();

    /**
     * Найти пункт правил дорожного движения по id. Если нет, то пустой.
     * @param id - пункт правил дорожного движения, тип int.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.Rule>}
     */
    Optional<Rule> findRuleById(int id);

    /**
     * Возращает список пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    List<Rule> findRuleAll();

    /**
     * Возращает список пунктов правил дорожного движения выбранных
     * в списке идентификаторов пунктов правил дорожного движения.
     * @param ids тип массив int. Содержит массив типа int.
     *            В каждой ячейке хранится идентификатор из списка
     *            пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    Set<Rule> findRulesByIds(int[] ids);
}
