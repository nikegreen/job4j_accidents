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
 * <p>Тесты IndexController class. Spring boot index controller</p>
 * @author nikez
 * @version $Id: $Id
 * Контрол для отображения главной страницы и обработки результатов страницы:
 *  - index.html
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * <p>Тест страницы сервиса для создания нового происшествия.</p>
     * param model тип {@link org.springframework.ui.Model}
     * всегда вернёт строку:
     * return типа {@link java.lang.String}  = "createAccident".
     * Через атрибут "user" передаём имя пользователя тип {@link  java.lang.String}.
     */
    @Test
    @WithMockUser
    public void testViewCreateAccident() throws Exception {
        assertThat(mockMvc).isNotNull();
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    /**
     * <p>Тест обработчика нажатия кнопки 'сохранить' в странице создания происшествия</p>
     * Получаем из формы объект {@param accident}
     * с заполненными полями типа {@link ru.job4j.accidents.model.Accident}
     * param model тип {@link org.springframework.ui.Model}
     * param accident тип {@link ru.job4j.accidents.model.Accident}
     * param req - получаем значения выбранных элементов списка статей нарушения.
     * return тип {@link java.lang.String} строка для перехода на другую страницу.
     * index - если без ошибок.
     * error - в если есть ошибки.
     */
    public void testSave() {

    }

    /**
     * <p>Тест страницы сервиса для редактирования происшествия.</p>
     * param model тип {@link org.springframework.ui.Model}
     * param id тип {@link }
     * всегда вернёт строку:
     * return типа {@link java.lang.String}  = "editAccident".
     * Через атрибут "user" передаём имя пользователя тип {@link  java.lang.String}.
     * --
     *     GetMapping("/editAccident")
     *     public String viewEditAccident(Model model, @RequestParam("id") int id)
     */
    @Test
    @WithMockUser
    public void testViewEditAccident() throws Exception {
        assertThat(mockMvc).isNotNull();
        this.mockMvc.perform(get("/editAccident?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"));
    }

    /**
     * <p>Тест обработчика нажатия кнопки 'сохранить' в странице редактирования происшествия</p>
     * Получаем из формы объект {@param accident}
     * с заполненными полями типа {@link ru.job4j.accidents.model.Accident}
     * param model тип {@link org.springframework.ui.Model}
     * param accident тип {@link ru.job4j.accidents.model.Accident}
     * param req - получаем значения выбранных элементов списка статей нарушения.
     * return тип {@link java.lang.String} строка для перехода на другую страницу.
     * index - если без ошибок.
     * error - в если есть ошибки.
     * --
     *     PostMapping("/updateAccident")
     *     public String updateAccident(
     *             Model model,
     *             ModelAttribute Accident accident,
     *             HttpServletRequest req)
     */
    public void testUpdateAccident() {

    }
}