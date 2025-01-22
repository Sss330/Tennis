package util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import model.entity.Player;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

            Configuration configuration = new Configuration();

            configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
            configuration.addAnnotatedClass(Player.class);
            configuration.configure();

            return configuration.buildSessionFactory();

    }

}
