package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.User;

import java.util.Optional;

/**
 * @author nikez
 * @version $Id: $Id
 * репозиторий для пользователей
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Поиск пользователя по имени
     * @param username - имя пользователя тип {@link java.lang.String}
     * @return пользователь {@link java.util.Optional<ru.job4j.accidents.model.User>}
     */
    Optional<User> findByUsername(String username);
}
