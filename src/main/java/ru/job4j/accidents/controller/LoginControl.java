package ru.job4j.accidents.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author nikez
 * @version $Id: $Id
 * Контроллер страницы авторизации пользователя
 */
@Controller
public class LoginControl {

    /**
     * Запрос страницы авторизации пользователя GET /reg
     * @param error - статус ошибки предыдущей регистрации (если была),
     *              передаём в {@param model} атрибутом "errorMessage"
     *              строку "Username or Password is incorrect !!".
     * @param logout - статус предыдущей де-авторизации (логаут)
     *              передаём в {@param model} атрибутом "errorMessage"
     *              строку "You have been successfully logged out !!".
     * @param model тип {@link org.springframework.ui.Model}
     * @return тип {@link java.lang.String} строка "login";
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    /**
     * Обработка де-авторизации пользователя GET /logout
     * @param request тип {@link javax.servlet.http.HttpServletRequest}
     * @param response тип {@link javax.servlet.http.HttpServletResponse}
     * @return тип {@link java.lang.String} строка "redirect:/login?logout=true";
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}
