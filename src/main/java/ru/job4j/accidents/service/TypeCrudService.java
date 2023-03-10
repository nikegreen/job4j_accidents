package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AbstractRepository;
import ru.job4j.accidents.repository.TypeCrudRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author nikez
 * @version $Id: $Id
 * Класс сервис для типов происшествий по количеству и участникам происшествия (AccidentType)
 */
@Service
public class TypeCrudService implements AbstractTypeService {
    private final AbstractRepository<AccidentType> typeRepository;

    public TypeCrudService(TypeCrudRepository typeRepository) {
        this.typeRepository = new AbstractRepository<>(typeRepository);
    }

    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public List<AccidentType> findAll() {
        return typeRepository.findAll();
    }

    /**
     * Найти тип происшествия по id. Если нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public Optional<AccidentType> findById(int id) {
        return typeRepository.findById(id);
    }
}
