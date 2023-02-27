package ru.job4j.accidents.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

/**
 * @author nikez
 * @version $Id: $Id
 * Конфигурация БД JDBC
 */
@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
public class JdbcConfig {

    /**
     * Бин для установки источника БД
     * @param driver тип {@link java.lang.String} название драйвера БД
     * @param url тип {@link java.lang.String} путь имя БД
     * @param username тип {@link java.lang.String} имя пользователя (логин) к БД
     * @param password тип {@link java.lang.String} пароль к БД
     * @return тип {@link javax.sql.DataSource} настроенный источник данных
     */
    @Bean
    public DataSource ds(@Value("${jdbc.driver}") String driver,
                         @Value("${jdbc.url}") String url,
                         @Value("${jdbc.username}") String username,
                         @Value("${jdbc.password}") String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    /**
     * Бин для установки JDBC репозитория
     * @param ds тип {@link javax.sql.DataSource} настроенный источник данных
     * @return тип {@link org.springframework.jdbc.core.JdbcTemplate}
     */
    @Bean
    public JdbcTemplate jdbc(DataSource ds) {
        return new JdbcTemplate(ds);
    }
}