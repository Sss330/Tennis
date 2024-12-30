package service;

import dao.PlayerDao;
import model.comon.MatchScore;
import model.comon.Score;


public class MatchScoreCalculationService {

    PlayerDao playerDao = new PlayerDao();

    //todo надо увеличить кол во очков у того кто выйграл очко
    public void winServe(MatchScore matchScore, long servedPlayerId) {

        //todo: матч заканчивается если игрок набивает  2 сета (((1 сет = 6 геймов) 1 гейм = 40 поинтов ) 1 winServe = 15, 30, 40 )

        //todo надо увеличить кол во очков у того кто выйграл очко


        Score firstPlayerScore = matchScore.getScoreFirstPlayer();

        int firstPlayersPoints = firstPlayerScore.getPoints();


        Score secondPlayerScore = matchScore.getScoreSecondPlayer();

        int secondPlayersPoints = secondPlayerScore.getPoints();


    }


    private void countScore(MatchScore score, long servedPlayerId) {

        Score winnerServeScore = score.getScoreFirstPlayer();
        Score loserServeScore = score.getScoreFirstPlayer();

        int points = winnerServeScore.getPoints();
        int games = winnerServeScore.getGames();
        int sets = winnerServeScore.getSets();

        long firstPlayerId = score.getFirstPlayerId();
        long secondPlayerId = score.getFirstPlayerId();


        if (points == 0) {

            winnerServeScore.setPoints(15);

        } else if (points == 15) {

            winnerServeScore.setPoints(30);

        } else if (points == 30) {

            winnerServeScore.setPoints(40);

        } else if (points == 40) {

            winnerServeScore.setPoints(0);
            winnerServeScore.setGames(++games);

        } else if (firstPlayerId == servedPlayerId) {



        } else if (true) {
            
        }
    }
}

    /*public boolean isMatchOver(MatchScore matchScore) {

        matchScore.getScoreFirstPlayer();
        matchScore.getScoreFirstPlayer();

        //if (matchScore.getScoreFirstPlayer())

        //todo поменять фолс
        return false;
    }*/




