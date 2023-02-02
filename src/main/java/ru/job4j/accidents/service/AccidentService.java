package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * @author nikez
 * @version $Id: $Id
 * Класс сервис для происшествий (Accident)
 */
@Service
public class AccidentService {
    private final AccidentRepository accidentRepository;

    public AccidentService(AccidentRepository accidentJdbcTemplate) {
        this.accidentRepository = accidentJdbcTemplate;
    }

    /**
     * Функция добавляет в хранилище новое происшествие. Затирает id новым значением.
     * Нет проверки на дубликаты.
     * @param accident - происшествие тип {@link ru.job4j.accidents.model.Accident}
     * @return результат добавления тип {@link boolean}
     * true  - добавлен
     * false - ошибка
     */
    public boolean add(Accident accident) {
        return accidentRepository.add(accident);
    }

    /**
     * Удаляет происшествие из хранилища по {@param id} тип {@link int}.
     * @param id - идентификатор положительное число больше 0.
     * @return результат удаления тип {@link boolean}
     * true  - успешно удалён.
     * false - ошибка или нет в хранилище.
     */
    public boolean delete(int id) {
        return accidentRepository.delete(id);
    }

    /**
     * Вернуть список всех происшествий
     * @return список типа {@link java.util.List<ru.job4j.accidents.model.Accident>}
     */
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    /**
     * Найти происшествие по {@param id}.
     * @param id id - идентификатор положительное число больше 0.
     * @return происшествие тип {@link java.util.Optional<ru.job4j.accidents.model.Accident>}
     * если не найдено, то результат Optional.Empty
     */
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    /**
     * Сохраняет значение из {@param accident} в хранилище.
     * @param accident - происшествие с новыми полями (кроме идентификатора происшествия)
     * @return результат обновления  тип boolean.
     * true  - обновлено в хранилище.
     * false - ошибка обновления. Может отсутствовать id.
     */
    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }

    /**
     * Найти тип происшествия по id. Емли нет, то пустой.
     * @param id - идентификатор типа происшествия.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.AccidentType>}
     */
    public Optional<AccidentType> findTypeById(int id) {
        return accidentRepository.findTypeById(id);
    }

    /**
     * Возращает список типов происшествий.
     * @return список {@link java.util.List<ru.job4j.accidents.model.AccidentType>}
     */
    public List<AccidentType> findTypeAll() {
        return accidentRepository.findTypeAll();
    }

    /**
     * Найти пункт правил дорожного движения по id. Если нет, то пустой.
     * @param id - пункт правил дорожного движения, тип int.
     * @return тип происшесвия {@link java.util.Optional<ru.job4j.accidents.model.Rule>}
     */
    public Optional<Rule> findRuleById(int id) {
        return accidentRepository.findRuleById(id);
    }

    /**
     * Возращает список пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    public List<Rule> findRuleAll() {
        return accidentRepository.findRuleAll();
    }

    /**
     * Возращает список пунктов правил дорожного движения выбранных
     * в списке идентификаторов пунктов правил дорожного движения.
     * @param ids тип массив int. Содержит массив типа int.
     *            В каждой ячейке хранится идентификатор из списка
     *            пунктов правил дорожного движения.
     * @return список {@link java.util.List<ru.job4j.accidents.model.Rule>}
     */
    public Set<Rule> findRulesByIds(int[] ids) {
        return accidentRepository.findRulesByIds(ids);
    }

    /**
     * Внутренняя функция для установки поля rules класса {@link ru.job4j.accidents.model.Accident}
     * @param accident тип {@link ru.job4j.accidents.model.Accident} содержит нарушение,
     *                 в котором нужно заполнить поле rules из {@param ids}.
     * @param ids массив типа {@link java.lang.String}[]. Массив строк с идентификаторами получаем
     *            из страницы от элемента тэг - select в режиме множественного выбора, имя "rIds".
     * @return тип boolean. Вернёт результат выполнения функции.
     * true  - поле заполнено.
     * false - не заполнено, ошибка.
     */
    public boolean setRules(Accident accident, String[] ids) {
        boolean result;
        try {
            accident.setRules(accidentRepository.findRulesByIds(Arrays.stream(ids)
                    .mapToInt(Integer::valueOf)
                    .toArray()));
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
