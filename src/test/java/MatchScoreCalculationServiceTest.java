import model.comon.MatchScore;
import model.comon.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.MatchScoreCalculationService;

import static org.junit.jupiter.api.Assertions.*;

class MatchScoreCalculationServiceTest {


    private MatchScoreCalculationService service;
    private MatchScore matchScore;
    private Score scoreFirst;
    private Score scoreSecond;
    private final long firstPlayerId = 1L;
    private final long secondPlayerId = 2L;

    @BeforeEach
    void setUp() {
        service = new MatchScoreCalculationService();
        scoreFirst = new Score();
        scoreSecond = new Score();
        scoreFirst.setPoints(0);
        scoreFirst.setGames(0);
        scoreFirst.setSets(0);
        scoreFirst.setTieBreak(false);
        scoreFirst.setAdvantage(false);
        scoreSecond.setPoints(0);
        scoreSecond.setGames(0);
        scoreSecond.setSets(0);
        scoreSecond.setTieBreak(false);
        scoreSecond.setAdvantage(false);
        matchScore = new MatchScore();
        matchScore.setFirstPlayerId(firstPlayerId);
        matchScore.setSecondPlayerId(secondPlayerId);
        matchScore.setScoreFirstPlayer(scoreFirst);
        matchScore.setScoreSecondPlayer(scoreSecond);
    }

    @Test
    void testUpdateScoreWhenMatchIsOver() {
        scoreFirst.setSets(2);
        service.updateScore(matchScore, firstPlayerId);
        assertEquals(0, scoreFirst.getPoints());
    }

    @Test
    void testPointIncrementFrom0To15() {
        service.updateScore(matchScore, firstPlayerId);
        assertEquals(15, scoreFirst.getPoints());
    }

    @Test
    void testPointIncrementFrom15To30() {
        scoreFirst.setPoints(15);
        service.updateScore(matchScore, firstPlayerId);
        assertEquals(30, scoreFirst.getPoints());
    }

    @Test
    void testPointIncrementFrom30To40() {
        scoreFirst.setPoints(30);
        service.updateScore(matchScore, firstPlayerId);
        assertEquals(40, scoreFirst.getPoints());
    }

    @Test
    void testGameWinWithoutDeuce() {
        scoreFirst.setPoints(40);
        scoreSecond.setPoints(30);
        service.updateScore(matchScore, firstPlayerId);
        assertEquals(0, scoreFirst.getPoints());
        assertEquals(0, scoreSecond.getPoints());
        assertEquals(1, scoreFirst.getGames());
    }

    @Test
    void testDeuceAndAdvantageScenario() {
        scoreFirst.setPoints(40);
        scoreSecond.setPoints(40);
        service.updateScore(matchScore, firstPlayerId);
        assertTrue(scoreFirst.isAdvantage());
        assertFalse(scoreSecond.isAdvantage());
        service.updateScore(matchScore, secondPlayerId);
        assertFalse(scoreFirst.isAdvantage());
        assertFalse(scoreSecond.isAdvantage());
        assertEquals(40, scoreFirst.getPoints());
        assertEquals(40, scoreSecond.getPoints());
        service.updateScore(matchScore, firstPlayerId);
        assertTrue(scoreFirst.isAdvantage());
        service.updateScore(matchScore, firstPlayerId);
        assertEquals(0, scoreFirst.getPoints());
        assertEquals(0, scoreSecond.getPoints());
        assertEquals(1, scoreFirst.getGames());
    }

    @Test
    void testTieBreakScenario() {
        scoreFirst.setGames(6);
        scoreSecond.setGames(6);
        scoreFirst.setTieBreak(true);
        scoreSecond.setTieBreak(true);
        scoreFirst.setPoints(7);
        scoreSecond.setPoints(5);
        service.updateScore(matchScore, firstPlayerId);
        assertEquals(0, scoreFirst.getPoints());
        assertEquals(0, scoreSecond.getPoints());
        assertEquals(0, scoreFirst.getGames());
        assertEquals(0, scoreSecond.getGames());
        assertFalse(scoreFirst.isTieBreak());
        assertFalse(scoreSecond.isTieBreak());
        assertEquals(1, scoreFirst.getSets());
    }

}