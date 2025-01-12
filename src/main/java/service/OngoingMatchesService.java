package service;

import dao.PlayerDao;
import model.comon.MatchScore;
import model.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final ConcurrentHashMap<UUID, MatchScore> ongoingMatches = new ConcurrentHashMap<>();

    PlayerDao playerDao = new PlayerDao();

    MatchScore match = new MatchScore();

    public MatchScore getMatchScoreByNamesOfPlayers(String firstNameOfPlayer, String secondNameOfPlayer) {

        Player player1 = playerDao.findByName(firstNameOfPlayer);
        if (player1 == null) {
            player1 = Player.builder()
                    .name(firstNameOfPlayer)
                    .build();
            playerDao.save(player1);
        }

        Player player2 = playerDao.findByName(secondNameOfPlayer);
        if (player2 == null) {
            player2 = Player.builder()
                    .name(secondNameOfPlayer)
                    .build();
            playerDao.save(player2);
        }

        match = MatchScore.builder()
                .firstPlayerId(player1.getId())
                .secondPlayerId(player2.getId())
                .build();

        return match;
    }

    public void saveMatch(UUID uuidOfMatch, MatchScore currentMatch) {
        ongoingMatches.put(uuidOfMatch, currentMatch);
    }


    public void deleteMatch(UUID codeOfMatch) {
        ongoingMatches.remove(codeOfMatch);
    }


    public MatchScore getMatch(UUID codeOfMatch) {
        return ongoingMatches.get(codeOfMatch);
    }

    public String getNamePlayerById(long idPlayer) {
        return playerDao.findPlayerById(idPlayer);
    }

}
