package ru.egormit.starshipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main-класс второго сервиса.
 *
 * @author Egor Mitrofanov.
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class StarshipServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StarshipServiceApplication.class);
    }

    /**
     * Тестов не будет.
     *
     * @param args только попробуй что-то передать сюда
     */
    public static void main(String[] args) {
        SpringApplication.run(StarshipServiceApplication.class, args);
    }

}
