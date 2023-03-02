package ru.job4j.accidents.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentCrudService;
import ru.job4j.accidents.service.RuleCrudService;
import ru.job4j.accidents.service.TypeCrudService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private AccidentCrudService accidents;
    @Autowired
    private TypeCrudService types;
    @Autowired
    private RuleCrudService rules;
    @Autowired
    private MockMvc mockMvc;

    /**
     * идентификатор происшествия
     */
    private int accidentId = 0;

    /**
     * происшествие
     */
    private Accident accident = null;

    /**
     * удаление происшествия после теста
     */
    @AfterEach
    public void afterEach() {
        if (accidentId != 0) {
            deleteAccident();
        }
    }

    /**
     * удаление происшествия перед тестом
     */
    @BeforeEach
    public void beforeEach() {
        if (accidentId != 0) {
            deleteAccident();
        }
    }

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
                .andExpect(view().name("createAccident"))
                .andExpect(model().attributeExists("types", "rules"))
                .andExpect(model().attribute("types", types.findAll()))
                .andExpect(model().attribute("rules", rules.findAll()));
    }


    /**
     * <p>Тест страницы сервиса для редактирования происшествия. Без ошибки</p>
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
    public void testViewEditAccidentOk() throws Exception {
        Accident accident = accidents.findById(1).orElse(null);
        assertThat(accident).isNotNull();
        this.mockMvc.perform(get("/editAccident?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"))
                .andExpect(model().attributeExists("accident", "types", "rules"))
                .andExpect(model().attribute("accident", accident))
                .andExpect(model().attribute("types", types.findAll()))
                .andExpect(model().attribute("rules", rules.findAll()));
    }

    /**
     * <p>Тест страницы сервиса для редактирования происшествия. С ошибкой</p>
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
    public void testViewEditAccidentError() throws Exception {
        assertThat(mockMvc).isNotNull();
        this.mockMvc.perform(get("/editAccident?id=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    /**
     * Вспомогательная функция
     * создает тестовую запись с инцидентом
     * присваивает идентификатор инцидента
     * и ссылку на созданный инцидент.
     * @return идентификатор инцидента
     */
    private int createAccident() {
        AccidentType accidentType = types.findById(1).orElse(null);
        Accident accident = new Accident();
        accident.setId(0);
        accident.setText("text");
        accident.setAddress("address");
        accident.setStatus(0);
        accident.setName("name");
        accident.setType(accidentType);
        //accident.setRules(Set.of());
        if (accidents.add(accident)) {
            this.accident = accident;
            accidentId = accident.getId();
        }
        return accidentId;
    }

    /**
     * Вспомогательная функция
     * удаляет тестовую запись с инцидентом
     * обнуляет идентификатор инцидента
     * и ссылку на удалённый инцидент.
     */
    private void deleteAccident() {
        if (accidents.delete(accidentId)) {
            accidentId = 0;
            this.accident = null;
        }
    }
}