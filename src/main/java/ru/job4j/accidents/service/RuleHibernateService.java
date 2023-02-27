package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleHibernate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author nikez
 * @version $Id: $Id
 * Класс сервис для  пунктов правил дорожного движения (Rule)
 * в БД Hibernate
 */
@Service
@RequiredArgsConstructor
public class RuleHibernateService implements AbstractRuleService {
    private final RuleHibernate rules;

    /**
     * Найти пункт правил дорожного движения по id. Если нет, то пустой.
     * @param id - пункт правил дорожного движения, тип int.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.Rule>}
     */
    @Override
    public Optional<Rule> findById(int id) {
        return rules.findById(id);
    }

    /**
     * Возращает список пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    @Override
    public List<Rule> findAll() {
        return rules.findAll();
    }

    /**
     * Возращает список пунктов правил дорожного движения выбранных
     * в списке идентификаторов пунктов правил дорожного движения.
     * @param ids тип массив int. Содержит массив типа int.
     *            В каждой ячейке хранится идентификатор из списка
     *            пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    @Override
    public Set<Rule> findRulesByIds(int[] ids) {
        return rules.findRulesByIds(ids);
    }
}
