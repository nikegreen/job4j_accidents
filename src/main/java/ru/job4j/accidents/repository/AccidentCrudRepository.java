package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.Optional;

/**
 * @author nikez
 * @version $Id: $Id
 * Класс хранилище в БД JPA для происшествий (Accident)
 */
public interface AccidentCrudRepository extends CrudRepository<Accident, Integer> {
    @EntityGraph(attributePaths = {"rules", "type"})
    @Override
    Optional<Accident> findById(Integer integer);
}
