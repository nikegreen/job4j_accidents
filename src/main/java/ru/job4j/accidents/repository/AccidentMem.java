package ru.job4j.accidents.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс хранилище в оперативной памяти для происшествий (Accident)
 */
@Repository
@RequiredArgsConstructor
public class AccidentMem implements AccidentRepository {
    private final AtomicInteger size = new AtomicInteger();
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<Integer, Accident>();

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
        return accidents.putIfAbsent(accident.getId(), accident) == accident;
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
}
