package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentRepository;
import java.util.List;
import java.util.Optional;

/**
 * Класс сервис для происшествий (Accident)
 */
@Service
public class AccidentService {
    private final AccidentRepository accidentRepository;

    public AccidentService(AccidentRepository accidentRepository) {
        this.accidentRepository = accidentRepository;
        add(new Accident(
                0,
                "Павлик Морозов",
                "Езда с мигалками по пешеходной зоне",
                "Москва, Красная площадь",
                accidentRepository.findTypeById(1).orElse(null)));
        add(new Accident(
                0,
                "Павлик Морозов",
                "Езда в нетрезвом виде",
                "Ленинград, Петровка 38",
                accidentRepository.findTypeById(2).orElse(null)));
        add(new Accident(
                0,
                "Дядя Стёпа",
                "не пропустил пешехода",
                "Москва, ВДНХ",
                accidentRepository.findTypeById(3).orElse(null)));
        add(new Accident(
                0,
                "Дядя Стёпа",
                "проезд по выделенной полосе для городского транспорта",
                "Москва, ВДНХ",
                accidentRepository.findTypeById(1).orElse(null)));
    }

    /**
     * Функция добавляет в хранилище новое происшествие. Затирает id новым значением.
     * Нет проверки на дубликаты.
     * @param accident - происшествие тип {@link ru.job4j.accidents.model.Accident}
     * @return результат добавления тип {@link boolean}
     * true  - добавлен
     * false - ошибка
     */
    public boolean add(Accident accident) {
        return accidentRepository.add(accident);
    }

    /**
     * Удаляет происшествие из хранилища по {@param id} тип {@link int}.
     * @param id - идентификатор положительное число больше 0.
     * @return результат удаления тип {@link boolean}
     * true  - успешно удалён.
     * false - ошибка или нет в хранилище.
     */
    public boolean delete(int id) {
        return accidentRepository.delete(id);
    }

    /**
     * Вернуть список всех происшествий
     * @return список типа {@link java.util.List<ru.job4j.accidents.model.Accident>}
     */
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    /**
     * Найти происшествие по {@param id}.
     * @param id id - идентификатор положительное число больше 0.
     * @return происшествие тип {@link java.util.Optional<ru.job4j.accidents.model.Accident>}
     * если не найдено, то результат Optional.Empty
     */
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    /**
     * Сохраняет значение из {@param accident} в хранилище.
     * @param accident - происшествие с новыми полями (кроме идентификатора происшествия)
     * @return результат обновления  тип boolean.
     * true  - обновлено в хранилище.
     * false - ошибка обновления. Может отсутствовать id.
     */
    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }

    /**
     * Найти тип происшествия по id. Емли нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    public Optional<AccidentType> findTypeById(int id) {
        return accidentRepository.findTypeById(id);
    }

    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    public List<AccidentType> findTypeAll() {
        return accidentRepository.findTypeAll();
    }
}
