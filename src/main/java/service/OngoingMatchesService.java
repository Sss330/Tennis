package service;

import dao.PlayerDao;
import dto.CurrentMatchDto;
import model.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final ConcurrentHashMap<UUID, CurrentMatchDto> ongoingMatches;
    PlayerDao playerDao = new PlayerDao();
    CurrentMatchDto match = new CurrentMatchDto();

    public OngoingMatchesService(ConcurrentHashMap<UUID, CurrentMatchDto> ongoingMatches) {
        this.ongoingMatches = ongoingMatches;
    }

    public CurrentMatchDto getCurrentMatchWithPlayers(String firstNameOfPlayer, String secondNameOfPlayer) {

        //сейвит, если нету в бд, или ищет по имени, если есть
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
        match = CurrentMatchDto.builder()
                .firstPlayerId(player1.getId())
                .secondPlayerId(player2.getId())
                .build();
        return match;
    }

    public void saveMatch(UUID codeOfMatch, CurrentMatchDto currentMatch) {
        ongoingMatches.put(codeOfMatch, currentMatch);
    }

    public void deleteMatch(UUID codeOfMatch) {

        ongoingMatches.remove(codeOfMatch);

    }

    public CurrentMatchDto getMatch(UUID codeOfMatch) {

        return ongoingMatches.get(codeOfMatch);

    }
}
