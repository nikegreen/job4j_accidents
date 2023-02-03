package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate implements RuleRepository {
    private final static String SQL_FIND_ALL =
            "select id, name from _rule";
    private static final String SQL_FIND_BY_ID =
            "select id, name from _rule where id = ?";
    private final JdbcTemplate jdbc;

    /**
     * класс мэппер для преобразования строки запроса в объект класса Rule.
     */
    private final RowMapper<Rule> ruleRowMapper = (resultSet, rowNum) -> {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("id"));
        rule.setName(resultSet.getString("name"));
        return rule;
    };

    /**
     * Возращает список пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    @Override
    public List<Rule> findAll() {
        return jdbc.query(SQL_FIND_ALL,
                ruleRowMapper);
    }

    /**
     * Найти пункт правил дорожного движения по id. Если нет, то пустой.
     * @param id - пункт правил дорожного движения, тип int.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.Rule>}
     */
    @Override
    public Optional<Rule> findById(int id) {
        Rule rule = this.jdbc.queryForObject(
                SQL_FIND_BY_ID,
                ruleRowMapper,
                id
        );
        return Optional.ofNullable(rule);
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
