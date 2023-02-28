package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author nikez
 * @version $Id: $Id
 * Тест контроллера страницы регистрации нового пользователя
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class RegControlTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Ответ страницы регистрации нового пользователя POST /reg
     * param user тип {@link ru.job4j.accidents.model.User} новый пользователь
     * return тип {@link java.lang.String} строка
     * "redirect:/login" - ОК
     * "redirect:/reg?error=true" - ошибка.
     * --
     *     PostMapping("/reg")
     *     public String regSave(@ModelAttribute User user)
     */
    void regSave() {
    }

    /**
     * Запрос страницы регистрации нового пользователя GET /reg
     * param error - статус ошибки предыдущей регистрации (если была),
     *              передаём в {@param model} атрибутом "errorMessage".
     * param model тип {@link org.springframework.ui.Model}
     * return тип {@link java.lang.String} строка "reg";
     * --
     *     GetMapping("/reg")
     *     public String regPage(@RequestParam(value = "error", required = false) String error,
     *                           Model model)
     */
    @Test
    @WithMockUser
    void regPage() throws Exception {
        assertThat(mockMvc).isNotNull();
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }
}