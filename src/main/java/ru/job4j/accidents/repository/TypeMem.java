package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TypeMem implements TypeRepository {
    private final List<AccidentType> types = new ArrayList<>(
            List.of(
                    new AccidentType(1, "Две машины"),
                    new AccidentType(2, "Машина и человек"),
                    new AccidentType(3, "Машина и велосипед")
            )
    );

    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public List<AccidentType> findAll() {
        return types;
    }

    /**
     * Найти тип происшествия по id. Если нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    @Override
    public Optional<AccidentType> findById(int id) {
        if (id < 1 || id > types.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(types.get(id - 1));
    }
}
