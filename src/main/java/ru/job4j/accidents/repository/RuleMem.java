package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RuleMem implements RuleRepository {
    private  final List<Rule> rules = List.of(
            new Rule(1, "Статья. 1"),
            new Rule(2, "Статья. 2"),
            new Rule(3, "Статья. 3")
    );

    @Override
    public List<Rule> findAll() {
        return rules;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return rules.stream().filter(rule -> rule.getId() == id).findFirst();
    }

    @Override
    public Set<Rule> findRulesByIds(int[] ids) {
        return rules.stream()
                .filter(
                        rule -> Arrays.stream(ids)
                                .filter(id -> rule.getId() == id)
                                .findFirst()
                                .isPresent()
                ).collect(Collectors.toSet());
    }
}
