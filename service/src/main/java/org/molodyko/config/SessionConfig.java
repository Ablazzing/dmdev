package org.molodyko.config;

import org.hibernate.SessionFactory;
import org.molodyko.HibernateConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {

    @Bean("sessionFactory")
    public SessionFactory getSessionFactory() {
        return HibernateConfig.getConfig().buildSessionFactory();
    }
}
