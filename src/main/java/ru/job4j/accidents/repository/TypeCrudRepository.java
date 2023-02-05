package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

public interface TypeCrudRepository extends CrudRepository<AccidentType, Integer> {
}
