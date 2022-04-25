package org.molodyko;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.molodyko.entity.Category;
import org.molodyko.entity.CategoryRename;
import org.molodyko.entity.DescriptionChanger;
import org.molodyko.entity.Entry;
import org.molodyko.entity.Holiday;
import org.molodyko.entity.HolidayType;
import org.molodyko.entity.User;

public class HibernateConfig {
    public static Configuration getConfig() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(HolidayType.class);
        configuration.addAnnotatedClass(Holiday.class);
        configuration.addAnnotatedClass(CategoryRename.class);
        configuration.addAnnotatedClass(DescriptionChanger.class);
        configuration.addAnnotatedClass(Entry.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();
        return configuration;
    }
}
