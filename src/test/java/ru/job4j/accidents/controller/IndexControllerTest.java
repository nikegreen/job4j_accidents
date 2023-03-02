package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.service.AccidentCrudService;
import ru.job4j.accidents.service.TypeCrudService;

/**
 * <p>Тесты IndexController class. Spring boot index controller</p>
 * @author nikez
 * @version $Id: $Id
 * Контрол для отображения главной страницы и обработки результатов страницы:
 *  - index.html
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class IndexControllerTest {
    @Autowired
    private AccidentCrudService accidents;
    @Autowired
    private TypeCrudService types;
//    @Autowired
//    private RuleCrudService rules;
    @Autowired
    private MockMvc mockMvc;

    /**
     * <p>тест Главная страница сервиса - GET /</p>
     * всегда вернёт
     * return типа {@link java.lang.String}  = "redirect:/index".
     */
    @Test
    @WithMockUser
    void index() throws Exception {
        assertThat(mockMvc).isNotNull();
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/index"));
    }

    /**
     * <p>Тест Главная страница сервиса - GET /index.</p>
     * всегда вернёт
     * return типа {@link java.lang.String}  = "index".
     */
    @Test
    @WithMockUser
    void testIndex() throws Exception {
        assertThat(mockMvc).isNotNull();

        this.mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("accidents", "types"))
//                .andExpect(model().attribute("accidents", accidents.findAll()))
                .andExpect(model().attribute("types", types.findAll()));
    }
}