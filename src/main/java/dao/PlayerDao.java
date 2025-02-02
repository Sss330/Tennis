package dao;

import exception.MatchNotFoundException;
import exception.PlayerNotFoundException;
import exception.SavingPlayerException;
import model.entity.Player;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;


public class PlayerDao implements CrudTennisDao<Player> {


    public Player findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return (session.createQuery("from Player where name = :name", Player.class).setParameter("name", name).uniqueResult());
        } catch (Exception e) {
            throw new PlayerNotFoundException("Не удалось найти игрока по имени " + e);
        }
    }

    public String getPlayerNameById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return String.valueOf(session.createQuery("from Player where id = :id", Player.class).setParameter("id",id).uniqueResult());
        } catch (Exception e) {
            throw new PlayerNotFoundException("Не удалось найти игрока по id " + e);
        }
    }

    @Override
    public void save(Player player) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(player);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new SavingPlayerException("Не удалось сохранить игрока " + e);
        }
    }

    @Override
    public List<Player> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("from Player", Player.class).list();
        } catch (Exception e) {
            throw new MatchNotFoundException("Не удалось найти все матчи " + e);
        }
    }
}
