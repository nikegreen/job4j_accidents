package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Set;

/**
 * @author nikez
 * @version $Id: $Id
 * модель данных описывающая происшествие (нарушение правил вождения)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {
    public enum Status {
        ACCEPT,
        DONE,
        ABORTED
    }

    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String text;
    private String address;
    private AccidentType type;
    private Set<Rule> rules;
    private int status;
}
