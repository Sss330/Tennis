package service;


import dao.MatchDao;

import model.entity.Match;


public class FinishedMatchesPersistenceService {

    MatchDao matchDao = new MatchDao();

    public void saveFinishedMatches(Match match) {
        matchDao.save(match);

    }

    public Match getMatchByNamePlayer(String nameOfPlayer) {

        Match match = matchDao.getMatchByPlayers(nameOfPlayer);

        return match;
    }
}
