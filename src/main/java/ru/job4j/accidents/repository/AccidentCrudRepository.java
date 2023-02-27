package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

/**
 * @author nikez
 * @version $Id: $Id
 * Класс хранилище в БД JPA для происшествий (Accident)
 */
public interface AccidentCrudRepository extends CrudRepository<Accident, Integer> {
}
