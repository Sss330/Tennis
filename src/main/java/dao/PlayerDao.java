package dao;

import lombok.Getter;
import model.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.List;
import java.util.Random;

@Getter
public class PlayerDao implements CrudTennisDao<Player> {


    public Player findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Player> query = session.createQuery("from Player where name = :name", Player.class);
            query.setParameter("name", name);

            Player player = query.uniqueResult();

            session.getTransaction().commit();
            return player;

        } catch (Exception e) {

            throw new RuntimeException("не удалось найти игрока по имени " + e);

        }
    }

    public String findPlayerById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Player> playerQuery = session.createQuery("from Player where id = :id", Player.class);
            playerQuery.setParameter("id", id);

            Player player = playerQuery.uniqueResult();

            session.getTransaction().commit();

            return (player != null) ? player.getName() : null;

        } catch (Exception e) {
            throw new RuntimeException("не получилось найти по id " + e);
        }
    }


    @Override
    public void save(Player player) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.save(player);
            session.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Player> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();


            Query<Player> query = session.createQuery("from Player", Player.class);

            session.getTransaction().commit();
            return query.getResultList();
        }
    }

}
