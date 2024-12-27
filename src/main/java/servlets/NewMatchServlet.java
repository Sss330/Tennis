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

    //OngoingMatchesService ongoingMatchesService

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String firstPlayer = req.getParameter("player-1");
        String secondPlayer = req.getParameter("player-2");


        if (firstPlayer == null || secondPlayer == null || firstPlayer.equals(secondPlayer)) {
            req.setAttribute("error", "имена игроков не могут быть одинаковыми или пустыми ");
            req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
            return;
        }


        UUID uuidOfMatch = UUID.randomUUID();


        // ongoingMatchesService.saveMatch(uuidOfMatch,ongoingMatchesService.getCurrentMatchWithPlayers(firstPlayer,secondPlayer));

        resp.sendRedirect("pages/match-score.jsp?uuid=" + uuidOfMatch);
    }
}
