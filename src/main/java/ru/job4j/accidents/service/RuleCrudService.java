package ru.job4j.accidents.service;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AbstractRepository;
import ru.job4j.accidents.repository.RuleCrudRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author nikez
 * @version $Id: $Id
 * Класс сервис для  пунктов правил дорожного движения (Rule)
 * в БД JPA
 */

@Service
public class RuleCrudService implements AbstractRuleService {
    private final AbstractRepository<Rule> rules;

    public RuleCrudService(RuleCrudRepository ruleRepository) {
        this.rules = new AbstractRepository<>(ruleRepository);
    }

    /**
     * Найти пункт правил дорожного движения по id. Если нет, то пустой.
     * @param id - пункт правил дорожного движения, тип int.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.Rule>}
     */

    public Optional<Rule> findById(int id) {
        return rules.findById(id);
    }

    /**
     * Возращает список пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
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
    public Set<Rule> findRulesByIds(int[] ids) {
        return  Streamable.of(
                rules.getRepository().findAllById(Arrays.stream(ids).boxed().toList())
        ).toSet();
    }
}
