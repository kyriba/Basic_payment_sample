package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class SpringBootStarter {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootStarter.class, args);

        try {
            context.getBean(Application.class).runApplication();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
