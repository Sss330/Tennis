package dao;

import exception.MatchNotFoundException;
import exception.MatchNotSaveException;
import model.entity.Match;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class MatchDao implements CrudTennisDao<Match> {


    public Match getMatchByPlayers(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Match> query = session.createQuery("FROM Match m WHERE m.player1.name = :name OR m.player2.name = :name", Match.class);
            query.setParameter("name", name);
            Match match = query.uniqueResult();

            session.getTransaction().commit();
            return match;
        } catch (Exception e) {
            throw new MatchNotFoundException("Не удалось найти матч по именам игроков " + e);
        }
    }

    public List<Match> findMatceshByFilter(String filterByPlayerName, int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String hql = """
                        SELECT m
                        FROM Match m
                        WHERE (:filter IS NULL OR :filter = '')
                           OR m.player1.name LIKE :likeName
                           OR m.player2.name LIKE :likeName
                           OR m.winner.name   LIKE :likeName
                        ORDER BY m.id DESC
                    """;

            Query<Match> query = session.createQuery(hql, Match.class);
            String likeName = (filterByPlayerName == null || filterByPlayerName.isBlank())
                    ? ""
                    : "%" + filterByPlayerName + "%";

            query.setParameter("filter", filterByPlayerName);
            query.setParameter("likeName", likeName);

            query.setFirstResult(offset);
            query.setMaxResults(limit);
            session.getTransaction().commit();

            return query.getResultList();
        } catch (MatchNotFoundException e) {
            throw new MatchNotFoundException("Не удалось найти матчи по фильтру " + e);
        }
    }

    public long countMatchesByFilter (String filterByPlayerName){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = """
                SELECT count(m)
                FROM Match m
                WHERE (:filter IS NULL OR :filter = '')
                   OR m.player1.name LIKE :likeName
                   OR m.player2.name LIKE :likeName
                   OR m.winner.name   LIKE :likeName
            """;
            Query<Long> query = session.createQuery(hql, Long.class);
            String likeName = (filterByPlayerName == null || filterByPlayerName.isBlank())
                    ? ""
                    : "%" + filterByPlayerName + "%";

            query.setParameter("filter", filterByPlayerName);
            query.setParameter("likeName", likeName);
            Long total = query.uniqueResult();
            session.getTransaction().commit();

            return total == null ? 0 : total;
        } catch (MatchNotFoundException e) {
            throw new MatchNotFoundException("Не удалось посчитать матчи по фильтру " + e);
        }
    }

    @Override
    public void save(Match match) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.save(match);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new MatchNotSaveException("Не удалось сохранить матч " + e);
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
            throw new MatchNotFoundException("Не удалось найти все матчи" + e);
        }
    }
}
