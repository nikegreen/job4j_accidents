package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

/**
 * @author nikez
 * @version $Id: $Id
 * репозиторий для типов происшествий (JPA).
 */
public interface TypeCrudRepository extends CrudRepository<AccidentType, Integer> {
}
