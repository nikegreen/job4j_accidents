package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import java.util.List;
import java.util.Optional;

/**
 * @author nikez
 * @version $Id: $Id
 * репозиторий для типов происшествий (Hibernate).
 */
@Repository
@AllArgsConstructor
public class TypeHibernate implements TypeRepository {
    private final SessionFactory sf;

    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public List<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType", AccidentType.class)
                    .list();
        }
    }

    /**
     * Найти тип происшествия по id. Если нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public Optional<AccidentType> findById(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType where id = :fId", AccidentType.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
        }
    }
}
