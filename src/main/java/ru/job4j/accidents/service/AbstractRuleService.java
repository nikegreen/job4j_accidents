package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author nikez
 * @version $Id: $Id
 * Интерфейс сервиса для пунктов правил дорожного движения (Rule)
 */
public interface AbstractRuleService {
    /**
     * Найти пункт правил дорожного движения по id. Если нет, то пустой.
     * @param id - пункт правил дорожного движения, тип int.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.Rule>}
     */
    Optional<Rule> findById(int id);

    /**
     * Возращает список пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    List<Rule> findAll();

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
