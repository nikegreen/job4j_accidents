package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentCrudService;
import ru.job4j.accidents.service.TypeCrudService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>IndexController class. Spring boot index controller</p>
 * @author nikez
 * @version $Id: $Id
 * Контрол для отображения главной страницы и обработки результатов страницы:
 *  - index.html
 */
@ThreadSafe
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final AccidentCrudService accidentService;
    private final TypeCrudService typeService;

    /**
     * <p>Главная страница сервиса -  index.</p>
     * всегда вернёт
     * @return типа {@link java.lang.String}  = "redirect:/index".
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    /**
     * <p>Главная страница сервиса -  index.</p>
     * всегда вернёт
     * @return типа {@link java.lang.String}  = "index".
     */
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute(
                "user",
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Accident> accidents = new ArrayList<>();
        accidentService.findAll().forEach(
                accident -> accidents.add(new Accident(
                        accident.getId(),
                        accident.getName(),
                        accident.getText(),
                        accident.getAddress(),
                        accident.getType(),
                        null,
                        accident.getStatus()
                        )
                )
        );
        model.addAttribute("accidents", accidents);
        model.addAttribute("types", typeService.findAll());
        return "index";
    }
}

