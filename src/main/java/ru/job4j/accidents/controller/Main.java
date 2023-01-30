package ru.job4j.accidents.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Основной класс - Main class. Sping boot приложение</p>
 * 1. Запустите приложение.
 * 2. Откройте страницу "http://localhost:8080/index" в веб броузере.
 * @author nikez
 * @version $Id: $Id
 */
@SpringBootApplication
public class Main {
    /**
     * <p>старт приложения main.</p>
     *
     * @param args массив строк {@link java.lang.String} Содержит один параметр в каждой строке.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }
}
