package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.comon.MatchScore;
import service.OngoingMatchesService;

import java.io.IOException;

import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();

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

        req.setAttribute("first-name-player", firstPlayer);
        req.setAttribute("second-name-player", secondPlayer);


        //вынести в отдельный метод
        if (firstPlayer == null || secondPlayer == null || firstPlayer.equals(secondPlayer)) {
            req.setAttribute("error", "Имена игроков не могут быть одинаковыми или пустыми ");
            req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
            return;
        }
        if (!Character.isUpperCase(firstPlayer.charAt(0)) || !Character.isUpperCase(secondPlayer.charAt(0))) {
            req.setAttribute("error", "Имена игроков должны начинаться с большой буквы ");
            req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
            return;
        }
        if (firstPlayer.contains(" ") || secondPlayer.contains(" ")){
            req.setAttribute("error", "Имена игроков не могут содержать пробелы ");
            req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
            return;
        }
        if (firstPlayer.length() > 32 || secondPlayer.length() > 32 ){
            req.setAttribute("error", "Имена игроков не могут содержать более 32-ух символов ");
            req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
            return;
        }
        if (!firstPlayer.matches("[А-Яа-яЁё]+") || !secondPlayer.matches("[А-Яа-яЁё]+") ){
            req.setAttribute("error", "Имена игроков могут содержать только буквы кириллицы ");
            req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
            return;
        }

        UUID uuidOfMatch = UUID.randomUUID();
        MatchScore matchScore = ongoingMatchesService.getMatchScoreByNamesOfPlayers(firstPlayer, secondPlayer);
        ongoingMatchesService.saveMatch(uuidOfMatch, matchScore);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuidOfMatch);
    }
}
