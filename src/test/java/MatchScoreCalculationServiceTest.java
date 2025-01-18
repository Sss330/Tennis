import model.comon.MatchScore;
import model.comon.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.MatchScoreCalculationService;

import static org.junit.jupiter.api.Assertions.*;

class MatchScoreCalculationServiceTest {

    private MatchScoreCalculationService matchScoreCalculationService;
    private MatchScore matchScore;


    //по умолчанию выйгрывает первый игрок 1L
    @BeforeEach
    void setUp() {
        matchScoreCalculationService = new MatchScoreCalculationService();
        matchScore = MatchScore.builder()
                .scoreFirstPlayer(new Score(0, 0, 0, false, false))
                .scoreSecondPlayer(new Score(0, 0, 0, false, false))
                .firstPlayerId(1L)
                .secondPlayerId(2L)
                .build();
    }

    @Test
    void testWinGameAt40_0() {
        matchScore.getScoreFirstPlayer().setPoints(40);
        matchScore.getScoreSecondPlayer().setPoints(0);
        matchScore.getScoreFirstPlayer().setGames(0);
        matchScore.getScoreSecondPlayer().setGames(0);
        matchScoreCalculationService.updateScore(matchScore, 1L);

        assertEquals(1, matchScore.getScoreFirstPlayer().getGames());
        assertEquals(0, matchScore.getScoreSecondPlayer().getGames());
        assertEquals(0, matchScore.getScoreFirstPlayer().getPoints());
        assertEquals(0, matchScore.getScoreSecondPlayer().getPoints());
    }

    @Test
    void testDeuceAt40_40() {
        matchScore.getScoreFirstPlayer().setPoints(40);
        matchScore.getScoreSecondPlayer().setPoints(40);
        matchScore.getScoreFirstPlayer().setAdvantage(false);
        matchScore.getScoreSecondPlayer().setAdvantage(false);
        matchScoreCalculationService.updateScore(matchScore, 1L);

        assertTrue(matchScore.getScoreFirstPlayer().isAdvantage());
        assertFalse(matchScore.getScoreSecondPlayer().isAdvantage());
    }

    @Test
    void testWinGameAfterAdvantage() {

        matchScore.getScoreFirstPlayer().setPoints(40);
        matchScore.getScoreSecondPlayer().setPoints(40);
        matchScore.getScoreFirstPlayer().setGames(5);
        matchScore.getScoreSecondPlayer().setGames(0);
        matchScore.getScoreFirstPlayer().setAdvantage(true);
        matchScore.getScoreSecondPlayer().setAdvantage(false);
        matchScore.getScoreFirstPlayer().setSets(1);
        matchScore.getScoreSecondPlayer().setSets(0);
        matchScoreCalculationService.updateScore(matchScore, 1L);

        assertEquals(0, matchScore.getScoreFirstPlayer().getPoints());
        assertEquals(0, matchScore.getScoreSecondPlayer().getPoints());
        assertEquals(0, matchScore.getScoreFirstPlayer().getGames());
        assertEquals(0, matchScore.getScoreSecondPlayer().getGames());
        assertFalse(matchScore.getScoreFirstPlayer().isAdvantage());
        assertFalse(matchScore.getScoreSecondPlayer().isAdvantage());
        assertEquals(2, matchScore.getScoreFirstPlayer().getSets());
        assertEquals(0, matchScore.getScoreSecondPlayer().getSets());

    }


    @Test
    void testWinSet() {
        matchScore.getScoreFirstPlayer().setPoints(40);
        matchScore.getScoreSecondPlayer().setPoints(15);
        matchScore.getScoreFirstPlayer().setGames(0);
        matchScore.getScoreSecondPlayer().setGames(0);
        matchScoreCalculationService.updateScore(matchScore, 1L);

        assertEquals(0, matchScore.getScoreFirstPlayer().getPoints());
        assertEquals(0, matchScore.getScoreSecondPlayer().getPoints());
        assertEquals(0, matchScore.getScoreSecondPlayer().getSets());

    }

}