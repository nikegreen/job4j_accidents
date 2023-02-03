package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentRule {
    @EqualsAndHashCode.Include
    private int id;
    @Column(name = "accident_id")
    private int accidentId;
    @Column(name = "rule_id")
    private int ruleId;
}
