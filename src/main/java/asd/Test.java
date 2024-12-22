package asd;

import model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

public class Test {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();


            Player player = Player.builder()
                    .name("Даниил Медведев")
                    .build();

            session.save(player);

            session.getTransaction().commit();
        }
    }
}
