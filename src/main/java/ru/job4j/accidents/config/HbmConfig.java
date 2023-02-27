package ru.job4j.accidents.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author nikez
 * @version $Id: $Id
 * Конфигурация БД Hibernate
 */
@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
public class HbmConfig {

    /**
     * Сканирование исходников программы, поиск загружаемых компонентов
     * их конструкторов и бинов
     * @param dialect тип строка содержит тип SQL запросов
     * @param ds тип {@link } источник БД
     * @return фабрику сессий БД тип {@link org.hibernate.SessionFactory}
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory(
            @Value("${hibernate.dialect}") String dialect,
            DataSource ds
    ) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(ds);
        sessionFactory.setPackagesToScan("ru.job4j.accidents.model");
        Properties cfg = new Properties();
        cfg.setProperty("hibernate.dialect", dialect);
        sessionFactory.setHibernateProperties(cfg);
        return sessionFactory;
    }

    /**
     * Бин для получения менеджера транзакций
     * @param sf фабрика сессий БД тип {@link org.hibernate.SessionFactory}
     * @return менеджера транзакций
     * тип {@link org.springframework.transaction.PlatformTransactionManager}
     */
    @Bean
    public PlatformTransactionManager htx(SessionFactory sf) {
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(sf);
        return tx;
    }
}
