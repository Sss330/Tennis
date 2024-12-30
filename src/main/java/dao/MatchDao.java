package dao;

import model.entity.Match;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.List;

public class MatchDao implements CrudTennisDao<Match> {


    public Match getMatchByPlayers(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Match> query = session.createQuery("FROM Match m WHERE m.player1.name = :player1Name AND m.player2.name = :player2Name", Match.class);

            query.setParameter("name", name);

            Match match = query.uniqueResult();

            session.getTransaction().commit();
            return match;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void save(Match match) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            session.save(match);

            session.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Match> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Match> query = session.createQuery("FROM Match", Match.class);
            List<Match> allMatches = query.getResultList();

            session.getTransaction().commit();
            return allMatches;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
