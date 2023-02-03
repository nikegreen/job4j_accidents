package ru.job4j.accidents.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {
    private final static String SQL_ADD =
            "insert into accident (name, _text, address, type_id, status)"
          + " values (?,?,?,?,?) RETURNING id, name, _text, address, type_id, status;";
    private final static String SQL_DELETE_BY_ID =
            "delete from accident where accident_id = ?";
    private final static String SQL_FIND_ALL =
            "select id, name, _text, address, type_id, status from accident";
    private static final String SQL_UPDATE =
            "update accident set name = ?, _text = ?, address = ?, type_id = ?, status = ?"
          + " where id = ?";
    private final static String SQL_ADD_RULE =
            "insert into accident_rule (accident_id, rule_id) values (?,?)";
    private final static String SQL_CLEAR_RULE =
            "delete from accident_rule where accident_id = ?";
    private final static String SQL_FIND_ALL_RULES_FOR_ID =
            "select rule_id from accident_rule where accident_id = ?";
    private static final String SQL_FIND_BY_ID =
            "select id, name, _text, address, type_id, status from accident where id = ?";

    private final JdbcTemplate jdbc;

    private final TypeJdbcTemplate types;

    /**
     * класс мэппер для преобразования строки запроса в объект класса Accident.
     */
    private final RowMapper<Accident> accidentRowMapper = (resultSet, rowNum) -> {
        Accident accident = new Accident();
        accident.setId(resultSet.getInt("id"));
        accident.setName(resultSet.getString("name"));
        accident.setText(resultSet.getString("_text"));
        accident.setAddress(resultSet.getString("address"));
        accident.setType(this.findTypeById(resultSet.getInt("type_id")).orElse(null));
        accident.setRules(findRulesForId(resultSet.getInt("id")));
        accident.setStatus(resultSet.getInt("status"));
        return accident;
    };

    /**
     * Функция добавляет в хранилище новое происшествие. Затирает id новым значением.
     * Нет проверки на дубликаты.
     * @param accident - происшествие тип {@link ru.job4j.accidents.model.Accident}
     * @return результат добавления тип {@link boolean}
     * true  - добавлен
     * false - ошибка
     */
    @Override
    public boolean add(Accident accident) {
        List<Accident> rows = jdbc.query(
                SQL_ADD,
                accidentRowMapper,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getStatus()
        );
        if (rows.size() != 1) {
            return false;
        }
        accident.setId(rows.get(0).getId());
        updateAccidentRules(accident);
        return true;
    }

    /**
     * Удаляет происшествие из хранилища по {@param id} тип {@link int}.
     * @param id - идентификатор положительное число больше 0.
     * @return результат удаления тип {@link boolean}
     * true  - успешно удалён.
     * false - ошибка или нет в хранилище.
     */
    @Override
    public boolean delete(int id) {
        int rows = jdbc.update(
                SQL_DELETE_BY_ID,
                id
        );
        jdbc.update(
                SQL_CLEAR_RULE,
                id
        );
        return rows > 0;
    }

    /**
     * Вспомогательная функция, для заполнения {@link ru.job4j.accidents.model.Accident}.rules.
     * @param id нарушения
     * @return список выбранных пунктов правил для нарушения с {@param id}
     * тип {@link java.util.Set<ru.job4j.accidents.model.Rule>}
     */
    private Set<Rule> findRulesForId(int id) {
        return Set.copyOf(jdbc.query(
                SQL_FIND_ALL_RULES_FOR_ID,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("rule_id"));
                    return rule;
                }, id));
    }

    /**
     * Вернуть список всех происшествий
     * @return список типа {@link java.util.List<ru.job4j.accidents.model.Accident>}
     */
    @Override
    public List<Accident> findAll() {
        return jdbc.query(SQL_FIND_ALL,
                accidentRowMapper);
    }

    /**
     * Найти происшествие по {@param id}.
     * @param  id - идентификатор положительное число больше 0.
     * @return происшествие тип {@link java.util.Optional<ru.job4j.accidents.model.Accident>}
     * если не найдено, то результат Optional.Empty
     */
    @Override
    public Optional<Accident> findById(int id) {
        Accident accident1 = this.jdbc.queryForObject(
                SQL_FIND_BY_ID,
                accidentRowMapper,
                id
        );
        return Optional.ofNullable(accident1);
    }

    /**
     * Сохраняет значение из {@param accident} в хранилище.
     * @param accident - происшествие с новыми полями (кроме идентификатора происшествия)
     * @return результат обновления  тип boolean.
     * true  - обновлено в хранилище.
     * false - ошибка обновления. Может отсутствовать id.
     */
    @Override
    public boolean update(Accident accident) {
        int rows = jdbc.update(
                SQL_UPDATE,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getStatus(),
                accident.getId()
        );
        updateAccidentRules(accident);
        return rows > 0;
    }

    /**
     * Найти тип происшествия по id. Если нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    private Optional<AccidentType> findTypeById(int id) {
        return types.findById(id);
    }

    /**
     * Обновляет список выбранных пунктов нарушений правил
     * в хранилище для поля {@param accident.rules}:
     * @param accident - объект который нужно поместить в
     *                 хранилище.
     */
    private void updateAccidentRules(Accident accident) {
        jdbc.update(
                SQL_CLEAR_RULE,
                accident.getId()
        );
        for (Rule rule : accident.getRules()) {
            jdbc.update(
                    SQL_ADD_RULE,
                    accident.getId(),
                    rule.getId()
            );
        }
    }
}
