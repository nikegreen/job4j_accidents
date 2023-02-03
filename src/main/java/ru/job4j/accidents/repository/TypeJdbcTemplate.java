package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TypeJdbcTemplate implements TypeRepository {
    private final static String SQL_FIND_ALL =
            "select id, name from accident_type";
    private static final String SQL_FIND_BY_ID =
            "select id, name from accident_type where id = ?";
    private final JdbcTemplate jdbc;

    /**
     * класс мэппер для преобразования строки запроса в объект класса AccidentType.
     */
    private final RowMapper<AccidentType> typeRowMapper = (resultSet, rowNum) -> {
        AccidentType type = new AccidentType();
        type.setId(resultSet.getInt("id"));
        type.setName(resultSet.getString("name"));
        return type;
    };

    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public List<AccidentType> findAll() {
        return jdbc.query(SQL_FIND_ALL,
                typeRowMapper);
    }

    /**
     * Найти тип происшествия по id. Если нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public Optional<AccidentType> findById(int id) {
        AccidentType type = this.jdbc.queryForObject(
                SQL_FIND_BY_ID,
                typeRowMapper,
                id
        );
        return Optional.ofNullable(type);
    }
}
