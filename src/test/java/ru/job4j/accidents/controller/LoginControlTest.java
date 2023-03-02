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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author nikez
 * @version $Id: $Id
 * Тесты контроллера страницы авторизации пользователя
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class LoginControlTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Запрос страницы авторизации пользователя GET /reg
     * param error - статус ошибки предыдущей регистрации (если была),
     *              передаём в {@param model} атрибутом "errorMessage"
     *              строку "Username or Password is incorrect !!".
     * param logout - статус предыдущей де-авторизации (логаут)
     *              передаём в {@param model} атрибутом "errorMessage"
     *              строку "You have been successfully logged out !!".
     * param model тип {@link org.springframework.ui.Model}
     * return тип {@link String} строка "login";
     * --
     *     GetMapping("/login")
     *     public String loginPage(RequestParam(value = "error", required = false) String error,
     *                             RequestParam(value = "logout", required = false) String logout,
     *                             Model model)
     */
    @Test
    @WithMockUser
    void loginPage() throws Exception {
        assertThat(mockMvc).isNotNull();
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    /**
     * Тест обработка де-авторизации пользователя GET /logout
     * param request тип {@link javax.servlet.http.HttpServletRequest}
     * param response тип {@link javax.servlet.http.HttpServletResponse}
     * return тип {@link String} строка "redirect:/login?logout=true";
     * --
     *    RequestMapping(value = "/logout", method = RequestMethod.GET)
     *    public String logoutPage(HttpServletRequest request, HttpServletResponse response)
     */
    @Test
    @WithMockUser
    void logoutPage() throws Exception {
        assertThat(mockMvc).isNotNull();
        this.mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login?logout=true"));
    }
}