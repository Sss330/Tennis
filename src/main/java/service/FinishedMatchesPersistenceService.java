package service;


import dao.MatchDao;

import exception.MatchNotFoundException;
import exception.SavingMatchException;
import model.entity.Match;

import java.util.List;
import java.util.Optional;

public class FinishedMatchesPersistenceService {
    MatchDao matchDao = new MatchDao();

    public void saveFinishedMatch(Match match) {
        try {
            matchDao.save(match);
        } catch (Exception e) {
            throw new SavingMatchException("Не удалось сохранить матч " + e);
        }
    }

    public Match getMatchByNamePlayer(String nameOfPlayer) {
        try {
            return matchDao.getMatchByPlayers(nameOfPlayer);
        } catch (MatchNotFoundException e) {
            throw new MatchNotFoundException("не удалось найти матч по именам игроков " + e);
        }
    }

    public List<Match> getAllMatches() {
        return matchDao.findAll();
    }

    public Optional<List<Match>> findMatchesByFilter(String filterByPlayerName, int offset, int limit) {
        try {
            String filter = (filterByPlayerName == null || filterByPlayerName.isBlank()) ? "" : filterByPlayerName;
            String likeName = (filterByPlayerName == null || filterByPlayerName.isBlank())
                    ? ""
                    : "%" + filterByPlayerName + "%";
            return matchDao.findMatchesByFilter(filter, likeName, offset, limit);
        } catch (MatchNotFoundException e) {
            throw new MatchNotFoundException("Не удалось найти матчи по фильтру " + e);
        }
    }

    public long countMatchesByFilter(String filterByPlayerName) {
        try {
            return matchDao.countMatchesByFilter(filterByPlayerName);
        } catch (MatchNotFoundException e) {
            throw new MatchNotFoundException("Не удалось посчитать матчи по фильтру" + e);
        }
    }

}
                                                                                            