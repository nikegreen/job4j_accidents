package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentCrudService;
import ru.job4j.accidents.service.RuleCrudService;
import ru.job4j.accidents.service.TypeCrudService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author nikez
 * @version $Id: $Id
 * Контрол для отображения страниц и обработки результатов страниц:
 *  - createAccident.html
 *  - editAccident.html
 */
@Controller
@AllArgsConstructor
public class AccidentControl {
    private final AccidentCrudService accidents;
    private final TypeCrudService types;
    private final RuleCrudService rules;

    /**
     * <p>Страница сервиса для создания нового происшествия.</p>
     * @param model тип {@link org.springframework.ui.Model}
     * всегда вернёт строку:
     * @return типа {@link java.lang.String}  = "createAccident".
     * Через атрибут "user" передаём имя пользователя тип {@link  java.lang.String}.
     */
    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", types.findAll());
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("rules", rules.findAll());
        return "createAccident";
    }

    /**
     * <p>Обработчик нажатия кнопки 'сохранить' в странице создания происшествия</p>
     * Получаем из формы объект {@param accident}
     * с заполненными полями типа {@link ru.job4j.accidents.model.Accident}
     * @param model тип {@link org.springframework.ui.Model}
     * @param accident тип {@link ru.job4j.accidents.model.Accident}
     * @param req - получаем значения выбранных элементов списка статей нарушения.
     * @return тип {@link java.lang.String} строка для перехода на другую страницу.
     * index - если без ошибок.
     * error - в если есть ошибки.
     */
    @PostMapping("/saveAccident")
    public String save(Model model,
                       @ModelAttribute Accident accident,
                       HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        model.addAttribute("user", "Petr Arsentev");
        if (accident == null) {
            return goToError(
                    model,
                    "Не получены данные происшествия, не сохранено в хранилище.",
                    "redirect:/index");
        }
        if (accident.getType() == null
                || types.findById(accident.getType().getId()).isEmpty()) {
            return goToError(
                    model,
                    "Не распознан тип происшествия. Не сохранено в хранилище.",
                    "redirect:/index"
            );
        }
        accident.setType(types.findById(accident.getType().getId()).orElse(null));
        if (!accidents.setRules(accident, ids)) {
            model.addAttribute("accident", accident);
            return goToError(
                    model,
                    "Не распознан список идентификаторов пунктов правил. Не сохранено в хранилище.",
                    "redirect:/index");
        }
        if (!accidents.add(accident)) {
            return goToError(
                    model,
                    "Не сохранено в хранилище.",
                    "redirect:/index"
            );
        }
        return "redirect:/index";
    }

    /**
     * <p>Страница сервиса для редактирования происшествия.</p>
     * @param model тип {@link org.springframework.ui.Model}
     * @param id тип {@link }
     * всегда вернёт строку:
     * @return типа {@link java.lang.String}  = "editAccident".
     * Через атрибут "user" передаём имя пользователя тип {@link  java.lang.String}.
     */
    @GetMapping("/editAccident")
    public String viewEditAccident(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", "Petr Arsentev");
        if (id < 1) {
            return goToError(
                    model,
                    "Не получен ID происшествия или меньше 1",
                    "redirect:/index");
        }
        Optional<Accident> accidentRes = accidents.findById(id);
        if (accidentRes.isEmpty()) {
            return goToError(
                    model,
                    "Происшествие с id=" + id + " не найдено",
                    "redirect:/index");
        }
        model.addAttribute("accident", accidentRes.get());
        model.addAttribute("types", types.findAll());
        model.addAttribute("rules", rules.findAll());
        return "editAccident";
    }

    /**
     * <p>Обработчик нажатия кнопки 'сохранить' в странице редактирования происшествия</p>
     * Получаем из формы объект {@param accident}
     * с заполненными полями типа {@link ru.job4j.accidents.model.Accident}
     * @param model тип {@link org.springframework.ui.Model}
     * @param accident тип {@link ru.job4j.accidents.model.Accident}
     * @param req - получаем значения выбранных элементов списка статей нарушения.
     * @return тип {@link java.lang.String} строка для перехода на другую страницу.
     * index - если без ошибок.
     * error - в если есть ошибки.
     */
    @PostMapping("/updateAccident")
    public String updateAccident(
            Model model,
            @ModelAttribute Accident accident,
            HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        model.addAttribute("user", "Petr Arsentev");
        if (accident == null) {
            return goToError(
                    model,
                    "Не получено содержание происшествия. Не сохранено в хранилище",
                    "redirect:/index");
        }
        if (accident.getType() == null
                || types.findById(accident.getType().getId()).isEmpty()) {
            return goToError(
                    model,
                    "Не распознан тип происшествия. Не сохранено в хранилище.",
                    "redirect:/index"
            );
        }
        int i = accident.getType().getId();
        AccidentType a = types.findById(i).orElse(null);
        accident.setType(a);
        //accident.setType(types.findById(accident.getType().getId()).orElse(null));
        if (!accidents.setRules(accident, ids)) {
            model.addAttribute("accident", accident);
            return goToError(
                    model,
                    "Не распознан список идентификаторов пунктов правил. Не сохранено в хранилище.",
                    "redirect:/index");
        }
        if (!accidents.update(accident)) {
            model.addAttribute("accident", accident);
            return goToError(
                    model,
                    "Не удалось обновить происшествие в хранилище",
                    "redirect:/index");
        }
        return "redirect:/index";
    }

    /**
     * <p>Функция для подготовки данных для перехода на страницу ошибки</p>
     * @param model тип {@link org.springframework.ui.Model} заполняем данными:
     * @param message тип {@link java.lang.String} сообщение для пользователя с описанием ошибки.
     *                Будет показано на странице ошибок.
     * @param link тип {@link java.lang.String} содержит ссылку страницы на которую нужно
     *             перенаправить после страницы с ошибкой.
     * @return тип {@link java.lang.String} результат функции, всегда страница ошибки.
     */
    private String goToError(Model model, String message, String link) {
        model.addAttribute("error", message);
        model.addAttribute("link", link);
        return "error";
    }
}
