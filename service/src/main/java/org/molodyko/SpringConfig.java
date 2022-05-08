package org.molodyko;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.molodyko")
public class SpringConfig {

    @Bean("sessionFactory")
    public SessionFactory getSessionFactory() {
        return HibernateConfig.getConfig().buildSessionFactory();
    }
}
