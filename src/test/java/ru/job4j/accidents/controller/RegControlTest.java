package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserCrudService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.mockito.ArgumentCaptor;
//import static org.hamcrest.Matchers.is;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author nikez
 * @version $Id: $Id
 * Тест контроллера страницы регистрации нового пользователя
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class RegControlTest {
    @MockBean
    private UserCrudService users;
    @Autowired
    private MockMvc mockMvc;

    /**
     * Тест ответа страницы регистрации нового пользователя POST /reg без ошибки
     * param user тип {@link User} новый пользователь
     * return тип {@link String} строка
     * "redirect:/login" - ОК
     * "redirect:/reg?error=true" - ошибка.
     * --
     *     PostMapping("/reg")
     *     public String regSave(@ModelAttribute User user)
     */
    @Test
    @WithMockUser
    void regSaveResultOk() throws Exception {
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        User user = new User();
        user.setUsername("new test user name");
        user.setPassword("1");
        Mockito.when(users.registration(argument.capture())).thenReturn(Optional.of(user));
        this.mockMvc.perform(
                post("/reg")
                        .param("username", "new test user name")
                        .param("password", "1")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        verify(users).registration(argument.capture());
        assertThat(argument.getValue().getUsername()).isEqualTo("new test user name");
        assertThat(argument.getValue().getPassword()).isEqualTo("1");
    }

    /**
     * Тест ответо страницы регистрации нового пользователя POST /reg с ошибкой
     * param user тип {@link User} новый пользователь
     * return тип {@link String} строка
     * "redirect:/login" - ОК
     * "redirect:/reg?error=true" - ошибка.
     * --
     *     PostMapping("/reg")
     *     public String regSave(@ModelAttribute User user)
     */
    @Test
    @WithMockUser
    void regSaveResultError() throws Exception {
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        User user = new User();
        user.setUsername("new test user name");
        user.setPassword("1");
        Mockito.when(users.registration(argument.capture())).thenReturn(Optional.empty());
        this.mockMvc.perform(
                        post("/reg")
                                .param("username", "new test user name")
                                .param("password", "1")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reg?error=true"));
        verify(users).registration(argument.capture());
        assertThat(argument.getValue().getUsername()).isEqualTo("new test user name");
        assertThat(argument.getValue().getPassword()).isEqualTo("1");
    }

    /**
     * Запрос страницы регистрации нового пользователя GET /reg
     * param error - статус ошибки предыдущей регистрации (если была),
     *              передаём в {@param model} атрибутом "errorMessage".
     * param model тип {@link org.springframework.ui.Model}
     * return тип {@link String} строка "reg";
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