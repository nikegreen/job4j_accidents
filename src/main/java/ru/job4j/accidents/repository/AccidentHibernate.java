package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentRule;
import ru.job4j.accidents.model.Rule;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentHibernate implements AccidentRepository {
    private final SessionFactory sf;

    public List<Accident> getAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Accident", Accident.class)
                    .list();
        }
    }

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
            try {
                var tx = session.beginTransaction();
                session.persist(accident);
                tx.commit();
                result = true;
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return result;
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
    }

    /**
     * Вернуть список всех происшествий
     * @return список типа {@link java.util.List<ru.job4j.accidents.model.Accident>}
     */
    @Override
    public List<Accident> findAll() {
        try (Session session = sf.openSession()) {
            List<Accident> result = session
                    .createQuery("from Accident a join fetch a.rules", Accident.class)
                    .list();
            return result;
        }
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
                    .createQuery("from Accident a join fetch a.rules where a.id = :fId",
                            Accident.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
            return result;
        }
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
        updateAccidentRules(accident);
        try (Session session = sf.openSession()) {
            try {
                var tx = session.beginTransaction();
                session.merge(accident);
                tx.commit();
                result = true;
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
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
            try {
                var tx = session.getTransaction();
                int rows = session.createQuery(
                        "delete from AccidentRule a where a.accidentId = :fId",
                        AccidentRule.class
                ).setParameter("fId", accident.getId()).executeUpdate();
                for (Rule rule : accident.getRules()) {
                    AccidentRule accidentRule = new AccidentRule();
                    accidentRule.setAccidentId(accident.getId());
                    accidentRule.setRuleId(rule.getId());
                    session.saveOrUpdate(accidentRule);
                }
                tx.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }
}