package dao;

import model.Match;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

import java.util.List;

public class MatchDao implements crudTennisDao<Match>{



    private Match getMatch(){

        return null;
    }

    private void updateMatch (){


    }

    private void deleteMatch(){


    }


    @Override
    public void add(Match match) {

        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            match = match.builder()
                    .player1(match.getPlayer1())
                    .player2(match.getPlayer2())
                    .winner(match.getWinner())
                    .build();

            session.save(match);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<Match> findAll() {
        return List.of();
    }
}
