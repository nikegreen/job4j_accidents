package ru.job4j.accidents.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author nikez
 * @version $Id: $Id
 * Конфигурация БД Spring JPA Hibernate
 */
@Configuration
@EnableJpaRepositories("ru.job4j.accidents.repository")
@EnableTransactionManagement
public class DataConfig {
    /**
     * Бин фабрики менеджера контейнера сущностей
     * @param ds источник данных {@link javax.sql.DataSource}
     * @return тип {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean}
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.job4j.accidents");
        factory.setDataSource(ds);
        return factory;
    }

    /**
     * Бин для получения менеджера транзакций
     * @param entityManagerFactory фабрика сессий БД
     *                             тип {@link javax.persistence.EntityManagerFactory}
     * @return менеджера транзакций
     * тип {@link org.springframework.transaction.PlatformTransactionManager}
     */
    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}