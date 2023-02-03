package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Set;
import javax.persistence.*;

/**
 * @author nikez
 * @version $Id: $Id
 * модель данных описывающая происшествие (нарушение правил вождения)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accident")
public class Accident {
    public enum Status {
        ACCEPT,
        DONE,
        ABORTED
    }

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String text;
    private String address;
    @ManyToOne
    @JoinColumn (name = "type_id")
    private AccidentType type;
    private Set<Rule> rules;
    private int status;
}
