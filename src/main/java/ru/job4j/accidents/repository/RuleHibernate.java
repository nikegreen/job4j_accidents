package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class RuleHibernate implements RuleRepository {
    private final SessionFactory sf;

    /**
     * Возращает список пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    @Override
    public List<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule", Rule.class)
                    .list();
        }
    }

    /**
     * Найти пункт правил дорожного движения по id. Если нет, то пустой.
     * @param id - пункт правил дорожного движения, тип int.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.Rule>}
     */
    @Override
    public Optional<Rule> findById(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule where id = :fId", Rule.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
        }
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
        return findAll().stream()
                .filter(
                        rule -> Arrays.stream(ids)
                                .filter(id -> rule.getId() == id)
                                .findFirst()
                                .isPresent()
                ).collect(Collectors.toSet());
    }
}
