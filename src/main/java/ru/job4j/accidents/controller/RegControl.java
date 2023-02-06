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

@Controller
@RequiredArgsConstructor
public class RegControl {
    private final UserCrudService users;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        if (users.registration(user).isEmpty()) {
            return "redirect:/reg?error=true";
        }
        return "redirect:/login";
    }

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
