package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayer = req.getParameter("имя первого игрока");
        String secondPlayer = req.getParameter("имя второго игрока");

        if (firstPlayer == null || secondPlayer == null || firstPlayer.isEmpty() || secondPlayer.isEmpty()) {
            req.setAttribute("errorMessage", "Both player names are required!");
            req.getRequestDispatcher("/new-match-form.jsp").forward(req, resp);
            return;
        }

        if (firstPlayer.equals(secondPlayer)) {
            req.setAttribute("errorMessage", "Players cannot have the same name!");
            req.getRequestDispatcher("/new-match-form.jsp").forward(req, resp);
            return;
        }


        log("New match started between: " + firstPlayer + " and " + secondPlayer);


        resp.sendRedirect("/matches");
    }
}
