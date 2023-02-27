package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

/**
 * @author nikez
 * @version $Id: $Id
 * репозиторий для пунктов правил дорожного движения (JPA).
 */
public interface RuleCrudRepository extends CrudRepository<Rule, Integer>  {
}
