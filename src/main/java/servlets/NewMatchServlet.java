package servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OngoingMatchesService;

import java.io.IOException;

import java.util.UUID;


@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uuidParam = req.getParameter("uuid");

        req.setAttribute("uuid", uuidParam);

        req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String firstPlayer = req.getParameter("playerOne");
        String secondPlayer = req.getParameter("playerTwo");


        if (firstPlayer == null || secondPlayer == null || firstPlayer.equals(secondPlayer)) {
            req.setAttribute("error", "имена игроков не могут быть одинаковыми или пустыми ");
            req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
            return;
        }

        UUID uuidOfMatch = UUID.randomUUID();

        ongoingMatchesService.saveMatch(uuidOfMatch, ongoingMatchesService.getMatchScoreWithPlayers(firstPlayer, secondPlayer));

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuidOfMatch);

    }
}
