package service;

import model.comon.MatchScore;
import model.comon.Score;

public class MatchScoreCalculationService {


    private static final int POINTS_0 = 0;
    private static final int POINTS_15 = 15;
    private static final int POINTS_30 = 30;
    private static final int POINTS_40 = 40;
    private static final int TIEBREAK_MIN_POINTS = 7;
    private static final int TIEBREAK_WIN_DIFF = 2;
    private static final int MAX_GAMES_BEFORE_TIEBREAK = 6;
    private static final int WINNING_GAMES_DIFF = 2;
    private static final int SET_WIN_GAMES = 7;
    private static final int MAX_SETS = 2;

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

        if (winnerScore.isTieBreak() && loserScore.isTieBreak()) {

            countTiebreak(winnerScore, loserScore);
            return;
        }
        switch (winnerScore.getPoints()) {
            case POINTS_0 -> winnerScore.setPoints(POINTS_15);
            case POINTS_15 -> winnerScore.setPoints(POINTS_30);
            case POINTS_30 -> winnerScore.setPoints(POINTS_40);
            case POINTS_40 -> handleDeuceOrGameWin(winnerScore, loserScore);
        }
    }

    private void handleDeuceOrGameWin(Score winnerScore, Score loserScore) {

        if (winnerScore.getPoints() == POINTS_40 && loserScore.getPoints() == POINTS_40) {

            if (loserScore.isAdvantage()) {
                winnerScore.setAdvantage(false);
                loserScore.setAdvantage(false);
            } else if (winnerScore.isAdvantage()) {
                resetPoints(winnerScore, loserScore);
                winnerScore.setGames(winnerScore.getGames() + 1);
                checkSetWin(winnerScore, loserScore);
            } else {
                winnerScore.setAdvantage(true);
                loserScore.setAdvantage(false);
            }
        } else if (winnerScore.isAdvantage()) {
            resetPoints(winnerScore, loserScore);
            winnerScore.setGames(winnerScore.getGames() + 1);
            checkSetWin(winnerScore, loserScore);
        } else {
            resetPoints(winnerScore, loserScore);
            winnerScore.setGames(winnerScore.getGames() + 1);
            checkSetWin(winnerScore, loserScore);
        }
    }

    public void checkSetWin(Score winnerScore, Score loserScore) {

        int gamesWinner = winnerScore.getGames();
        int gamesLoser = loserScore.getGames();

        if (gamesWinner == MAX_GAMES_BEFORE_TIEBREAK && gamesLoser == MAX_GAMES_BEFORE_TIEBREAK) {
            winnerScore.setTieBreak(true);
            loserScore.setTieBreak(true);
            return;
        }

        if ((gamesWinner >= MAX_GAMES_BEFORE_TIEBREAK && gamesWinner - gamesLoser >= WINNING_GAMES_DIFF) || gamesWinner == SET_WIN_GAMES) {
            winnerScore.setGames(0);
            loserScore.setGames(0);
            winnerScore.setSets(winnerScore.getSets() + 1);
        }
    }

    private void countTiebreak(Score winnerScore, Score loserScore) {

        winnerScore.setPoints(winnerScore.getPoints() + 1);

        if (winnerScore.getPoints() >= TIEBREAK_MIN_POINTS && winnerScore.getPoints() - loserScore.getPoints() >= TIEBREAK_WIN_DIFF) {
            resetPoints(winnerScore, loserScore);
            winnerScore.setGames(0);
            loserScore.setGames(0);
            winnerScore.setSets(winnerScore.getSets() + 1);
            resetTiebreak(winnerScore, loserScore);
        }
    }

    private void resetTiebreak(Score winnerScore, Score loserScore) {

        winnerScore.setTieBreak(false);
        loserScore.setTieBreak(false);
    }

    private void resetPoints(Score winnerScore, Score loserScore) {

        winnerScore.setPoints(0);
        loserScore.setPoints(0);
        winnerScore.setAdvantage(false);
        loserScore.setAdvantage(false);
    }

    public boolean isMatchOver(MatchScore matchScore) {
        return matchScore.getScoreFirstPlayer().getSets() == MAX_SETS || matchScore.getScoreSecondPlayer().getSets() == MAX_SETS;
    }
}
