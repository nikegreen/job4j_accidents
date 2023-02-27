package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserCrudService;

/**
 * @author nikez
 * @version $Id: $Id
 * Контроллер страницы регистрации нового пользователя
 */
@Controller
@RequiredArgsConstructor
public class RegControl {
    private final UserCrudService users;

    /**
     * Ответ страницы регистрации нового пользователя POST /reg
     * @param user тип {@link ru.job4j.accidents.model.User} новый пользователь
     * @return тип {@link java.lang.String} строка
     * "redirect:/login" - ОК
     * "redirect:/reg?error=true" - ошибка.
     */
    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        if (users.registration(user).isEmpty()) {
            return "redirect:/reg?error=true";
        }
        return "redirect:/login";
    }

    /**
     * Запрос страницы регистрации нового пользователя GET /reg
     * @param error - статус ошибки предыдущей регистрации (если была),
     *              передаём в {@param model} атрибутом "errorMessage".
     * @param model тип {@link org.springframework.ui.Model}
     * @return тип {@link java.lang.String} строка "reg";
     */
    @GetMapping("/reg")
    public String regPage(@RequestParam(value = "error", required = false) String error,
                          Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Username is busy or database error!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "reg";
    }
}
