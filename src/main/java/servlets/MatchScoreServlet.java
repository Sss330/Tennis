package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.comon.MatchScore;
import service.MatchScoreCalculationService;
import service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;


@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

       MatchScore currentMatch = ongoingMatchesService.getMatch(uuid);
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
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        long servedPlayerId = Long.parseLong(req.getParameter("served-player_id"));
        MatchScore currentMatch = ongoingMatchesService.getMatch(uuid);
        matchScoreCalculationService.updateScore(currentMatch, servedPlayerId);

        if (matchScoreCalculationService.isMatchOver(currentMatch)) {
            ongoingMatchesService.saveMatch(uuid, currentMatch);
            ongoingMatchesService.deleteMatch(uuid);

            resp.sendRedirect("pages/matches.jsp");
        }else {
            String firstPlayerName = ongoingMatchesService.getNamePlayerById(currentMatch.getFirstPlayerId());
            String secondPlayerName = ongoingMatchesService.getNamePlayerById(currentMatch.getSecondPlayerId());

            req.setAttribute("uuid", uuid.toString());
            req.setAttribute("match", currentMatch);
            req.setAttribute("firstPlayerName", firstPlayerName);
            req.setAttribute("secondPlayerName", secondPlayerName);
            req.getRequestDispatcher("pages/match-score.jsp").forward(req, resp);
        }
    }
}
