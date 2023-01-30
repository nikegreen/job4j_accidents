package ru.job4j.accidents.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>IndexController class. Spring boot index controller</p>
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Controller
public class IndexController {
    /**
     * <p>Главная страница сервиса -  index.</p>
     * всегда вернёт
     * @return типа {@link java.lang.String}  = "index".
     */
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        return "index";
    }
}

