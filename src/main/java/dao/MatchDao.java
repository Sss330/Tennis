package dao;

import exception.MatchNotFoundException;
import exception.SavingMatchException;
import model.entity.Match;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class MatchDao implements CrudTennisDao<Match> {

    private final String hqlQueryToGetMatches = """
                SELECT m
                FROM Match m
                WHERE (:filter IS NULL OR :filter = '')
                   OR m.firstPlayer.name LIKE :likeName
                   OR m.secondPlayer.name LIKE :likeName
                   OR m.winner.name LIKE :likeName
                ORDER BY m.id DESC
            """;
    private final String hqlToCountMatches = """
                SELECT count(m)
                FROM Match m
                WHERE (:filter IS NULL OR :filter = '')
                   OR m.firstPlayer.name LIKE :likeName
                   OR m.secondPlayer.name LIKE :likeName
                   OR m.winner.name LIKE :likeName
            """;

    public Match getMatchByPlayers(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            return session.createQuery("FROM Match m WHERE m.firstPlayer.name = :name OR m.secondPlayer.name = :name", Match.class).setParameter("name", name).uniqueResult();
        } catch (Exception e) {
            throw new MatchNotFoundException("Не удалось найти матч по именам игроков " + e);
        }
    }

    public Optional<List<Match>> findMatchesByFilter(String filter, String likeName, int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Match> query = session.createQuery(hqlQueryToGetMatches, Match.class);

            query.setParameter("filter", filter);
            query.setParameter("likeName", likeName);

            query.setFirstResult(offset);
            query.setMaxResults(limit);

            return Optional.ofNullable(query.getResultList());
        } catch (MatchNotFoundException e) {
            throw new MatchNotFoundException("Не удалось найти матчи по фильтру " + e);
        }
    }

    public long countMatchesByFilter(String filterByPlayerName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Long> query = session.createQuery(hqlToCountMatches, Long.class);
            String likeName = (filterByPlayerName == null || filterByPlayerName.isBlank())
                    ? ""
                    : "%" + filterByPlayerName + "%";

            query.setParameter("filter", filterByPlayerName);
            query.setParameter("likeName", likeName);

            return query.uniqueResult();
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
            throw new SavingMatchException("Не удалось сохранить матч " + e);
        }
    }

    @Override
    public List<Match> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("FROM Match", Match.class).getResultList();
        } catch (Exception e) {
            throw new MatchNotFoundException("Не удалось найти все матчи" + e);
        }
    }
}
