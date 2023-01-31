package ru.job4j.accidents.controller;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentControl {
    private final AccidentService accidents;

    /**
     * <p>Страница сервиса для создания нового происшествия.</p>
     * всегда вернёт строку:
     * @return типа {@link java.lang.String}  = "createAccident".
     * Через атрибут "user" передаём имя пользователя тип {@link  java.lang.String}.
     */
    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(Model model, @ModelAttribute Accident accident) {
        model.addAttribute("user", "Petr Arsentev");
        if (accident == null) {
            return goToError(
                    model,
                    "Не получены данные происшествия, не сохранено в хранилище.",
                    "/index");
        }
        if (!accidents.add(accident)) {
            return goToError(
                    model,
                    "Не сохранено в хранилище.",
                    "/index"
            );
        }
        return "redirect:/index";
    }

    /**
     * <p>Страница сервиса для редактирования происшествия.</p>
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
                    "/index");
        }
        Optional<Accident> accidentRes = accidents.findById(id);
        if (accidentRes.isEmpty()) {
            return goToError(
                    model,
                    "Происшествие с id=" + id + " не найдено",
                    "/index");
        }
        model.addAttribute("accident", accidentRes.get());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String updateAccident(Model model, @ModelAttribute Accident accident) {
        model.addAttribute("user", "Petr Arsentev");
        if (accident == null) {
            return goToError(
                    model,
                    "Не получено содержание происшествия. Не сохранено в хранилище",
                    "/index");
        }
        if (!accidents.update(accident)) {
            model.addAttribute("accident", accident);
            return goToError(
                    model,
                    "Не удалось обновить происшествие в хранилище",
                    "/index");
        }
        return "redirect:/index";
    }

    private String goToError(Model model, String message, String link) {
        model.addAttribute("error", message);
        model.addAttribute("link", link);
        return "error";
    }
}
