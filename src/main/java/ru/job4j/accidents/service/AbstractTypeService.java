package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentType;
import java.util.List;
import java.util.Optional;

public interface AbstractTypeService {
    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    List<AccidentType> findAll();

    /**
     * Найти тип происшествия по id. Если нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    Optional<AccidentType> findById(int id);
}
