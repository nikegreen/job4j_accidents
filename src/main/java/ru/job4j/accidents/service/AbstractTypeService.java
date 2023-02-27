package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentType;
import java.util.List;
import java.util.Optional;

/**
 * @author nikez
 * @version $Id: $Id
 * интерфейс сервиса для типов происшествий по количеству и участникам происшествия (AccidentType)
 * Сервис типов происшествий
 */
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
