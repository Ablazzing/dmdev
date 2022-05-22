package org.molodyko;

import org.molodyko.entity.User;
import org.molodyko.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class);
        UserRepository repository = context.getBean(UserRepository.class);
        User user = repository.findById(1).orElseThrow();
        System.out.println(user);
    }
}
