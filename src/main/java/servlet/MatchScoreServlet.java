package servlet;

import exception.MatchNotSaveException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.comon.MatchScore;
import model.entity.Match;
import model.entity.Player;
import service.FinishedMatchesPersistenceService;
import service.MatchScoreCalculationService;
import service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;


@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        MatchScore currentMatch = ongoingMatchesService.getMatchScore(uuid);

        String firstPlayerName = ongoingMatchesService.getNamePlayerById(currentMatch.getFirstPlayerId());
        String secondPlayerName = ongoingMatchesService.getNamePlayerById(currentMatch.getSecondPlayerId());

        req.setAttribute("uuid", uuid.toString());
        req.setAttribute("match", currentMatch);
        req.setAttribute("firstPlayerName", firstPlayerName);
        req.setAttribute("secondPlayerName", secondPlayerName);

        req.getRequestDispatcher("pages/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        if (uuidParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("error", "UUID утерян ");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(uuidParam);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("error", "Не правильный UUID формат ");
            return;
        }

        MatchScore currentMatchScore = ongoingMatchesService.getMatchScore(uuid);
        if (currentMatchScore == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("error", "Не удалось найти матч по UUID ");
            return;
        }

        long servedPlayerId;
        try {
            servedPlayerId = Long.parseLong(req.getParameter("served-player_id"));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("error", "Не удалось найти id игрока выйгравшего подачу ");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        matchScoreCalculationService.updateScore(currentMatchScore, servedPlayerId);

        if (matchScoreCalculationService.isMatchOver(currentMatchScore)) {

            Player player1 = new Player(currentMatchScore.getFirstPlayerId(), ongoingMatchesService.getNamePlayerById(currentMatchScore.getFirstPlayerId()));
            Player player2 = new Player(currentMatchScore.getSecondPlayerId(), ongoingMatchesService.getNamePlayerById(currentMatchScore.getSecondPlayerId()));
            Player winner = new Player(servedPlayerId, ongoingMatchesService.getNamePlayerById(servedPlayerId));

            Match match = Match.builder()
                    .player1(player1)
                    .player2(player2)
                    .winner(winner)
                    .build();

            try {
                finishedMatchesPersistenceService.saveFinishedMatch(match);
            } catch (MatchNotSaveException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                req.setAttribute("error", "Не удалось сохранить матч ");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
                return;
            }
            ongoingMatchesService.deleteMatch(uuid);

            resp.sendRedirect(req.getContextPath() + "/matches");

        } else {
            String firstPlayerName = ongoingMatchesService.getNamePlayerById(currentMatchScore.getFirstPlayerId());
            String secondPlayerName = ongoingMatchesService.getNamePlayerById(currentMatchScore.getSecondPlayerId());

            req.setAttribute("uuid", uuid.toString());
            req.setAttribute("match", currentMatchScore);
            req.setAttribute("firstPlayerName", firstPlayerName);
            req.setAttribute("secondPlayerName", secondPlayerName);
            req.getRequestDispatcher("pages/match-score.jsp").forward(req, resp);
        }
    }

}
