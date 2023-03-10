package ru.egormit.starship.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main-класс второго сервиса.
 *
 * @author Egor Mitrofanov.
 */
@SpringBootApplication
public class SecondServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SecondServiceApplication.class);
    }

    /**
     * Тестов не будет.
     *
     * @param args только попробуй что-то передать сюда
     */
    public static void main(String[] args) {
        SpringApplication.run(SecondServiceApplication.class, args);
    }

}
