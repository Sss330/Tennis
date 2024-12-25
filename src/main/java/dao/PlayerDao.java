package dao;

import lombok.Getter;
import model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.List;

@Getter
public class PlayerDao implements CrudTennisDao<Player> {


    public Player findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Player> query = session.createQuery("from Player where name = :name", Player.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        }

    }


    @Override
    public void save(Player player) {
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); Session session = sessionFactory.openSession()) {

            session.save(player);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Player> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Player> query = session.createQuery("from Player", Player.class);

            return query.getResultList();
        }
    }

}
