package ru.job4j.accidents.repository;

import lombok.Getter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import java.util.List;
import java.util.Optional;

public class AbstractRepository<T> {
    @Getter
    private final CrudRepository<T, Integer> repository;

    public AbstractRepository(CrudRepository<T, Integer> crudRepository) {
        repository = crudRepository;
    }

    public boolean add(T t) {
        t = repository.save(t);
        return t != null;
    }

    public boolean update(T t) {
        t = repository.save(t);
        return t != null;
    }

    public Optional<T> findById(int id) {
        return repository.findById(id);
    }

    public List<T> findAll() {
        return  Streamable.of(repository.findAll()).toList();
    }

    public boolean delete(int id) {
        repository.deleteById(id);
        return true;
    }
}
