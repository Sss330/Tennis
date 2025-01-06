package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Match;
import service.FinishedMatchesPersistenceService;

import java.io.IOException;

@WebServlet("/matches")
public class PlayedMatchesServlet extends HttpServlet {

    FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("pages/matches.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String filter_by_player_name = req.getParameter("filter_by_player_name");
        String pageParam = req.getParameter("page");


        Match match = finishedMatchesPersistenceService.getMatchByNamePlayer(filter_by_player_name);

        int page = Integer.parseInt(pageParam);

        req.setAttribute("page", page);
        req.setAttribute("filter_by_player_name", filter_by_player_name);


    }
}
