package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;

/**
 * <p>IndexController class. Spring boot index controller</p>
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final AccidentService accidentService;

    /**
     * <p>Главная страница сервиса -  index.</p>
     * всегда вернёт
     * @return типа {@link java.lang.String}  = "index".
     */
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}

