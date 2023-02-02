package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Класс хранилище в оперативной памяти для происшествий (Accident)
 */
@Repository
@RequiredArgsConstructor
public class AccidentMem implements AccidentRepository {
    private final AtomicInteger size = new AtomicInteger();
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<Integer, Accident>();
    private final List<AccidentType> types = new ArrayList<>(
            List.of(
                    new AccidentType(1, "Две машины"),
                    new AccidentType(2, "Машина и человек"),
                    new AccidentType(3, "Машина и велосипед")
            )
    );
    private  final List<Rule> rules = List.of(
            new Rule(1, "Статья. 1"),
            new Rule(2, "Статья. 2"),
            new Rule(3, "Статья. 3")
    );

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
        accident.setId(size.incrementAndGet());
        Accident res = accidents.putIfAbsent(accident.getId(), accident);
        return  res == null;
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
        return accidents.remove(id) != null;
    }

    /**
     * Вернуть список всех происшествий
     * @return список типа {@link java.util.List<ru.job4j.accidents.model.Accident>}
     */
    @Override
    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    /**
     * Найти происшествие по {@param id}.
     * @param  id - идентификатор положительное число больше 0.
     * @return происшествие тип {@link java.util.Optional<ru.job4j.accidents.model.Accident>}
     * если не найдено, то результат Optional.Empty
     */
    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.getOrDefault(id, null));
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
        Accident res = accidents.replace(accident.getId(), accident);
        return  accident.equals(res);
    }

    /**
     * Найти тип происшествия по id. Емли нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public Optional<AccidentType> findTypeById(int id) {
        if (id < 1 || id > types.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(types.get(id - 1));
    }

    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public List<AccidentType> findTypeAll() {
        return types;
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
