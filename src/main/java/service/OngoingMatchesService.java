package service;

import dao.PlayerDao;
import exception.MatchNotFoundException;
import exception.PlayerNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.comon.MatchScore;
import model.comon.Score;
import model.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OngoingMatchesService {

    private final ConcurrentHashMap<UUID, MatchScore> ongoingMatches = new ConcurrentHashMap<>();

    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();

    PlayerDao playerDao = new PlayerDao();


    public MatchScore getMatchScoreByNamesOfPlayers(String firstNameOfPlayer, String secondNameOfPlayer) {

        try {
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

            return MatchScore.builder()
                    .firstPlayerId(player1.getId())
                    .secondPlayerId(player2.getId())
                    .scoreFirstPlayer(new Score())
                    .scoreSecondPlayer(new Score())
                    .build();
        } catch (PlayerNotFoundException e) {
            throw new PlayerNotFoundException("Не удалось найти игроков по именам " + e);
        }
    }

    public void saveMatch(UUID uuidOfMatch, MatchScore currentMatch) {
        ongoingMatches.put(uuidOfMatch, currentMatch);
    }


    public void deleteMatch(UUID codeOfMatch) {
        ongoingMatches.remove(codeOfMatch);
    }

    public MatchScore getMatchScore(UUID codeOfMatch) {
        try {
            return ongoingMatches.get(codeOfMatch);
        } catch (MatchNotFoundException e) {
            throw new MatchNotFoundException("Не удалось найти матч по uuid " + e);
        }
    }

    public String getNamePlayerById(long idPlayer) {

        try {
            return playerDao.getPlayerNameById(idPlayer);
        } catch (PlayerNotFoundException e) {
            throw new PlayerNotFoundException("Не удалось найти игрока по id " + e);
        }

    }

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }
}
