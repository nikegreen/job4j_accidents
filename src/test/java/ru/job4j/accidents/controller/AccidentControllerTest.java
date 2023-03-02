package ru.job4j.accidents.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentCrudService;
import ru.job4j.accidents.service.RuleCrudService;
import ru.job4j.accidents.service.TypeCrudService;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
     * return типа {@link String}  = "createAccident".
     * Через атрибут "user" передаём имя пользователя тип {@link  String}.
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
     * <p>Тест обработчика нажатия кнопки 'сохранить' в странице создания происшествия</p>
     * Получаем из формы объект {@param accident}
     * с заполненными полями типа {@link Accident}
     * param model тип {@link org.springframework.ui.Model}
     * param accident тип {@link Accident}
     * param req - получаем значения выбранных элементов списка статей нарушения.
     * return тип {@link String} строка для перехода на другую страницу.
     * index - если без ошибок.
     * error - в если есть ошибки.
     */
    @Test
    @WithMockUser
    public void testSave() throws Exception {
        if (accidentId != 0) {
            deleteAccident();
        }
        assertThat(accidentId).isEqualTo(0);
        assertThat(accident).isNull();

        Rule rule1 = rules.findById(1).orElse(null);
        Rule rule3 = rules.findById(3).orElse(null);

        AccidentType accidentType = types.findById(1).orElse(null);
        List<Accident> accidentList = accidents.findAll();
        int size = accidentList.size();

        Accident accident2 = new Accident();
        accident2.setText("text");
        accident2.setAddress("address");
        accident2.setStatus(0);
        accident2.setName("name");
        accident2.setType(accidentType);
        accident2.setRules(Set.of(rule1, rule3));
        this.mockMvc.perform(
                        post("/saveAccident")
                                .param("id", "0")
                                .param("name", "name")
                                .param("text", "text")
                                .param("address", "address")
                                .param("type.id", "1")
                                .param("rIds", "1", "3")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
        accidentList = accidents.findAll();
        assertThat(accidentList.size()).isEqualTo(size + 1);
        Accident accident3 = accidentList.stream()
                .max(Comparator.comparingInt(Accident::getId))
                .orElse(null);
        accidentId = accident3.getId();
        accident = accidents.findById(accidentId).orElse(null);
        assertThat(accident).isNotNull();
        assertThat(accident.getName()).isEqualTo(accident2.getName());
        assertThat(accident.getText()).isEqualTo(accident2.getText());
        assertThat(accident.getAddress()).isEqualTo(accident2.getAddress());
        assertThat(accident.getType().getId()).isEqualTo(accident2.getType().getId());
        assertThat(accident.getType().getName()).isEqualTo(accident2.getType().getName());
        assertThat(accident.getStatus()).isEqualTo(accident2.getStatus());
        assertThat(accident.getRules().size()).isEqualTo(accident2.getRules().size());
        assertThat(
                accident.getRules()
                .stream()
                .sorted(Comparator.comparingInt(Rule::getId))
                .toList()
                .get(0)
                .getId()
        ).isEqualTo(accident2.getRules()
                .stream()
                .sorted(Comparator.comparingInt(Rule::getId))
                .toList()
                .get(0)
                .getId()
        );
        assertThat(
                accident.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(0)
                        .getName()
        ).isEqualTo(
                accident2.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(0)
                        .getName()
        );
        assertThat(
                accident.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(1)
                        .getId()
        ).isEqualTo(
                accident2.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(1)
                        .getId()
        );
        assertThat(
                accident.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(1)
                        .getName()
        ).isEqualTo(
                accident2.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(1)
                        .getName()
        );
    }

    /**
     * <p>Тест страницы сервиса для редактирования происшествия. Без ошибки</p>
     * param model тип {@link org.springframework.ui.Model}
     * param id тип {@link }
     * всегда вернёт строку:
     * return типа {@link String}  = "editAccident".
     * Через атрибут "user" передаём имя пользователя тип {@link  String}.
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
     * return типа {@link String}  = "editAccident".
     * Через атрибут "user" передаём имя пользователя тип {@link  String}.
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
     * <p>Тест обработчика нажатия кнопки 'сохранить' в странице редактирования происшествия</p>
     * Получаем из формы объект {@param accident}
     * с заполненными полями типа {@link Accident}
     * param model тип {@link org.springframework.ui.Model}
     * param accident тип {@link Accident}
     * param req - получаем значения выбранных элементов списка статей нарушения.
     * return тип {@link String} строка для перехода на другую страницу.
     * index - если без ошибок.
     * error - в если есть ошибки.
     * --
     *     PostMapping("/updateAccident")
     *     public String updateAccident(
     *             Model model,
     *             ModelAttribute Accident accident,
     *             HttpServletRequest req)
     */
    @Test
    @WithMockUser
    public void testUpdateAccident() throws Exception {
        if (accidentId == 0) {
            createAccident();
        }
        assertThat(accidentId).isNotEqualTo(0);
        assertThat(accident).isNotNull();

        Rule rule1 = rules.findById(1).orElse(null);
        Rule rule3 = rules.findById(3).orElse(null);

        int[] ints = {1, 3};

        ArgumentCaptor<String[]> argumentIds = ArgumentCaptor.forClass(String[].class);
        AccidentType accidentType = types.findById(1).orElse(null);

        ArgumentCaptor<Accident> argumentAccident = ArgumentCaptor.forClass(Accident.class);
        String[] rIds = {"1", "3"};
        Accident accident2 = new Accident();
        accident2.setId(accident.getId());
        accident2.setText("text");
        accident2.setAddress("address");
        accident2.setStatus(1);
        accident2.setName("name");
        accident2.setType(accidentType);
        accident2.setRules(Set.of(rule1, rule3));
        this.mockMvc.perform(
                        post("/updateAccident")
                                .param("id",  String.valueOf(accident.getId()))
                                .param("name", "name")
                                .param("text", "text")
                                .param("address", "address")
                                .param("type.id", "1")
                                .param("rIds", "1", "3")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        Accident accident3 = accidents.findById(accident.getId()).orElse(null);
        assertThat(accident3).isNotNull();
        assertThat(accident3.getName()).isEqualTo(accident.getName());
        assertThat(accident3.getText()).isEqualTo(accident.getText());
        assertThat(accident3.getAddress()).isEqualTo(accident.getAddress());
        assertThat(accident3.getType().getId()).isEqualTo(accident.getType().getId());
        assertThat(accident3.getType().getName()).isEqualTo(accident.getType().getName());
        assertThat(accident3.getStatus()).isEqualTo(accident.getStatus());
        assertThat(accident3.getRules().size()).isEqualTo(accident2.getRules().size());
        assertThat(
                accident3.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(0)
                        .getId()
        ).isEqualTo(
                accident2.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(0)
                        .getId()
        );
        assertThat(
                accident3.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(0)
                        .getName()
        ).isEqualTo(
                accident2.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(0)
                        .getName()
        );
        assertThat(
                accident3.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(1)
                        .getId()
        ).isEqualTo(
                accident2.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(1)
                        .getId()
        );
        assertThat(
                accident3.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(1)
                        .getName()
        ).isEqualTo(
                accident2.getRules()
                        .stream()
                        .sorted(Comparator.comparingInt(Rule::getId))
                        .toList()
                        .get(1)
                        .getName()
        );
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