package service;

import dao.PlayerDao;
import model.comon.MatchScore;
import model.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private ConcurrentHashMap<UUID, MatchScore> ongoingMatches;

    PlayerDao playerDao = new PlayerDao();

    MatchScore match = new MatchScore();

    //todo: не понимаю что нужно указать в конструкторе чтобы, нормально вызвать объект класса
    //todo: идея - поменять параметры конструктора

    public MatchScore getMatchScoreWithPlayers(String firstNameOfPlayer, String secondNameOfPlayer) {

        //сейвит, если нету в бд, или ищет по имени, если есть

        //todo подумать над опшиналами
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


    public void saveMatch(UUID codeOfMatch, MatchScore currentMatch) {

        ongoingMatches.put(codeOfMatch, currentMatch);

    }


    public void deleteMatch(UUID codeOfMatch) {

        ongoingMatches.remove(codeOfMatch);

    }


    public MatchScore getMatch(UUID codeOfMatch) {

        return ongoingMatches.get(codeOfMatch);

    }
}
