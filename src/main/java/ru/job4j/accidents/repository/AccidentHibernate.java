package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentRule;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AccidentHibernate implements AccidentRepository {
//    private final static String SQL_ADD =
//            "insert into accident (name, _text, address, type_id, status)"
//                    + " values (?,?,?,?,?) RETURNING id, name, _text, address, type_id, status;";
//    private final static String SQL_DELETE_BY_ID =
//            "delete from accident where accident_id = ?";
//    private final static String SQL_FIND_ALL =
//            "select id, name, _text, address, type_id, status from accident";
//    private static final String SQL_UPDATE =
//            "update accident set name = ?, _text = ?, address = ?, type_id = ?, status = ?"
//                    + " where id = ?";
    private final static String SQL_ADD_RULE =
            "insert into accident_rule (accident_id, rule_id) values (?,?)";
    private final static String SQL_CLEAR_RULE =
            "delete from accident_rule where accident_id = ?";
//    private final static String SQL_FIND_ALL_RULES_FOR_ID =
//            "select rule_id from accident_rule where accident_id = ?";
//    private static final String SQL_FIND_BY_ID =
//            "select id, name, _text, address, type_id, status from accident where id = ?";

//    private final JdbcTemplate jdbc;
    private final SessionFactory sf;

//    private final List<AccidentType> types = new ArrayList<>(
//            List.of(
//                    new AccidentType(1, "Две машины"),
//                    new AccidentType(2, "Машина и человек"),
//                    new AccidentType(3, "Машина и велосипед")
//            )
//    );
    private  final List<Rule> rules = List.of(
            new Rule(1, "Статья. 1"),
            new Rule(2, "Статья. 2"),
            new Rule(3, "Статья. 3")
    );

    public List<Accident> getAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Accident", Accident.class)
                    .list();
        }
    }

    /**
     * класс мэппер для преобразования строки запроса в объект класса Accident.
     */
    /*
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
    */
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
        boolean result = false;
        try (Session session = sf.openSession()) {
            session.save(accident);
            session.flush();
            result = true;
        }
        return result;
/*
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
 */
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
        boolean result = false;
        try (Session session = sf.openSession()) {
            session.createQuery(
                    "delete from AccidentRule where accident_id = :fId", AccidentRule.class)
                    .setParameter("fId", id)
                    .executeUpdate();
            int rows = session.createQuery("delete from Accident where id = :fId", Accident.class)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.flush();
            result = rows == 1;
        }
        return result;
        /*
        int rows = jdbc.update(
                SQL_DELETE_BY_ID,
                id
        );
        jdbc.update(
                SQL_CLEAR_RULE,
                id
        );
        return rows > 0;
         */
    }

    /**
     * Вспомогательная функция, для заполнения {@link ru.job4j.accidents.model.Accident}.rules.
     * @param id нарушения
     * @return список выбранных пунктов правил для нарушения с {@param id}
     * тип {@link java.util.Set<ru.job4j.accidents.model.Rule>}
     */
    private Set<Rule> findRulesForId(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentRule", AccidentRule.class)
                    .list()
                    .stream()
                    .filter(ar -> ar.getAccidentId() == id)
                    .map(ar -> findRuleById(ar.getRuleId()).orElse(null))
                    .filter(ar -> ar != null)
                    .collect(Collectors.toSet());
        }
/*
        return Set.copyOf(jdbc.query(
                SQL_FIND_ALL_RULES_FOR_ID,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("rule_id"));
                    return rule;
                }, id));
 */
    }

    /**
     * Вернуть список всех происшествий
     * @return список типа {@link java.util.List<ru.job4j.accidents.model.Accident>}
     */
    @Override
    public List<Accident> findAll() {
        try (Session session = sf.openSession()) {
            List<Accident> result = session
                    .createQuery("from Accident", Accident.class)
                    .list();
            result.forEach(accident -> accident.setRules(findRulesForId(accident.getId())));
            return result;
        }
/*
        return jdbc.query(SQL_FIND_ALL,
                accidentRowMapper);

 */
    }

    /**
     * Найти происшествие по {@param id}.
     * @param  id - идентификатор положительное число больше 0.
     * @return происшествие тип {@link java.util.Optional<ru.job4j.accidents.model.Accident>}
     * если не найдено, то результат Optional.Empty
     */
    @Override
    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            Optional<Accident> result = session
                    .createQuery("from Accident where id = :fId", Accident.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
            result.ifPresent(accident -> accident.setRules(findRulesForId(accident.getId())));
            return result;
        }
        /*
        Accident accident1 = this.jdbc.queryForObject(
                SQL_FIND_BY_ID,
                accidentRowMapper,
                id
        );
        return Optional.ofNullable(accident1);
         */
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
        boolean result = false;
        try (Session session = sf.openSession()) {
            updateAccidentRules(accident);
            session.update(accident);
            session.flush();
            result = true;
        }
        return result;
        /*
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
         */
    }

    /**
     * Найти тип происшествия по id. Если нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        Optional<AccidentType> result = Optional.empty();
        try (Session session = sf.openSession()) {
            AccidentType type = session.get(AccidentType.class, id);
            session.flush();
            result = Optional.ofNullable(type);
        }
        return result;
        /*
        return result;
        if (id < 1 || id > types.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(types.get(id - 1));
         */
    }

    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public List<AccidentType> findTypeAll() {
        List<AccidentType> result = List.of();
        try (Session session = sf.openSession()) {
            result = session.createQuery("from AccidentType", AccidentType.class).list();
        }
        return result;
    }

    /**
     * Обновляет список выбранных пунктов нарушений правил
     * в хранилище для поля {@param accident.rules}:
     * @param accident - объект который нужно поместить в
     *                 хранилище.
     */
    private void updateAccidentRules(Accident accident) {
        try (Session session = sf.openSession()) {
            session.createQuery(
                    "delete from AccidentRule where accident_id = :fId",
                    AccidentRule.class
            ).executeUpdate();
            for (Rule rule : accident.getRules()) {
                AccidentRule accidentRule = new AccidentRule();
                accidentRule.setAccidentId(accident.getId());
                accidentRule.setRuleId(rule.getId());
                session.save(accidentRule);
            }
        }
        /*
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
        */
    }

    /**
     * Найти пункт правил дорожного движения по id. Если нет, то пустой.
     * @param id - пункт правил дорожного движения, тип int.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.Rule>}
     */
    @Override
    public Optional<Rule> findRuleById(int id) {
        return rules.stream().filter(rule -> rule.getId() == id).findFirst();
    }

    /**
     * Возращает список пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    @Override
    public List<Rule> findRuleAll() {
        return rules;
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
        return rules.stream()
                .filter(
                        rule -> Arrays.stream(ids)
                                .filter(id -> rule.getId() == id)
                                .findFirst()
                                .isPresent()
                ).collect(Collectors.toSet());
    }

}