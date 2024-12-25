package servlets;

import dao.PlayerDao;
import dto.NewMatchDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Match;
import model.Player;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {


    PlayerDao playerDao = new PlayerDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //todo проверить если такие игроки в бд findByName
        //todo если есть просто достать из бд, иначе - сохранить
        //todo создать текущий Match<uuid,Match> и сохранить с айдишником
        //todo
        String firstPlayer = req.getParameter("player-1");
        String secondPlayer = req.getParameter("player-2");


        if (firstPlayer == null || secondPlayer == null || firstPlayer.equals(secondPlayer)) {
            req.setAttribute("error", "имена игроков не могут быть одинаковыми или пустыми ");
            req.getRequestDispatcher("pages/new-match.jsp").forward(req, resp);
            return;
        }


        Player player1 = playerDao.findByName(firstPlayer);
        if (player1 == null) {

            player1 = Player.builder()
                    .name(firstPlayer)
                    .build();

            playerDao.save(player1);
        }

        Player player2 = playerDao.findByName(secondPlayer);
        if (player2 == null) {

            player2 = Player.builder()
                    .name(secondPlayer)
                    .build();

            playerDao.save(player2);
        }
        UUID uuid = UUID.randomUUID();

        ConcurrentHashMap<UUID, Match> currentMatch = new ConcurrentHashMap<>();

       /* Match match = Match.builder()
                .player1(player1.getId())
                .player2(player2.getId())
                .winner()
                .build();

        currentMatch.put(uuid, match);*/

        resp.sendRedirect("pages/match-score.jsp?uuid=" + uuid);
    }
}
