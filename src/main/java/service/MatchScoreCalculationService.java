package service;


import model.comon.MatchScore;
import model.comon.Score;

public class MatchScoreCalculationService {


    //todo поменять магические числа на константы
    //todo поменять иф элсы на свитч кейсы

    public void updateScore(MatchScore matchScore, long servedPlayerId) {

        if (isMatchOver(matchScore)) {
            return;
        }
        countScore(matchScore, servedPlayerId);
    }

    private void countScore(MatchScore score, long servedPlayerId) {

        Score winnerScore;
        Score loserScore;

        if (score.getFirstPlayerId() == servedPlayerId) {

            winnerScore = score.getScoreFirstPlayer();
            loserScore = score.getScoreSecondPlayer();

        } else {

            winnerScore = score.getScoreSecondPlayer();
            loserScore = score.getScoreFirstPlayer();

        }

        int pointsWinner = winnerScore.getPoints();

        int pointsLoser = loserScore.getPoints();

        int gamesWinner = winnerScore.getGames();

        int gamesLoser = loserScore.getGames();

        int setsWinner = winnerScore.getSets();


        if (pointsWinner == 0) {

            winnerScore.setPoints(15);

        } else if (pointsWinner == 15) {

            winnerScore.setPoints(30);

        } else if (pointsWinner == 30) {

            winnerScore.setPoints(40);

        } else if (pointsWinner == 40 && pointsLoser == 40) {

            if (winnerScore.isAdvantage()) {

                winnerScore.setPoints(0);
                loserScore.setPoints(0);
                winnerScore.setAdvantage(false);
                winnerScore.setGames(++gamesWinner);

            } else {

                winnerScore.setAdvantage(true);
                loserScore.setAdvantage(false);

            }
        } else if (winnerScore.isAdvantage() || pointsWinner == 40) {

            winnerScore.setPoints(0);
            loserScore.setPoints(0);
            winnerScore.setGames(++gamesWinner);

        }

        if (gamesWinner >= 6 && gamesLoser <= 4 || gamesWinner == 7) {

            winnerScore.setGames(0);
            loserScore.setGames(0);
            winnerScore.setSets(++setsWinner);

        }

        //запускается тай брейк
        if (!score.getScoreFirstPlayer().isTieBreak() && gamesWinner == 6 && gamesLoser == 6) {
            winnerScore.setTieBreak(true);
            loserScore.setTieBreak(true);

            countTieBreakPoints(winnerScore, loserScore);
        }
    }

    private void countTieBreakPoints(Score winnerScore, Score loserScore) {

        if (winnerScore.getPoints() >= 7 && winnerScore.getPoints() - loserScore.getPoints() >= 2) {

            winnerScore.setGames(0);
            loserScore.setGames(0);
            winnerScore.setSets(winnerScore.getSets() + 1);
            winnerScore.setPoints(0);
            loserScore.setPoints(0);

            winnerScore.setTieBreak(false);
            loserScore.setTieBreak(false);
        } else {
            winnerScore.setPoints(winnerScore.getPoints() + 1);
        }


    }

    public boolean isMatchOver(MatchScore matchScore) {

        int setsFirstPlayer = matchScore.getScoreFirstPlayer().getSets();
        int setsSecondPlayer = matchScore.getScoreSecondPlayer().getSets();


        return setsFirstPlayer == 2 || setsSecondPlayer == 2;
    }
}
