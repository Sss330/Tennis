package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Match;
import service.FinishedMatchesPersistenceService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/matches")
public class PlayedMatchesServlet extends HttpServlet {

    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String filterByPlayerName = req.getParameter("filter_by_player_name");
        String pageParam = req.getParameter("page");


        int currentPage = 1;
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        if (currentPage < 1) {
            currentPage = 1;
        }
        int offset = (currentPage - 1) * PAGE_SIZE;

        Optional<List<Match>> matches = finishedMatchesPersistenceService.findMatchesByFilter(filterByPlayerName, offset, PAGE_SIZE);

        long totalMatches = finishedMatchesPersistenceService
                .countMatchesByFilter(filterByPlayerName);

        int totalPages = (int) Math.ceil((double) totalMatches / PAGE_SIZE);
        req.setAttribute("matches", matches);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("filter_by_player_name", filterByPlayerName == null ? "" : filterByPlayerName);

        req.getRequestDispatcher("pages/matches.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/matches");
    }
}

