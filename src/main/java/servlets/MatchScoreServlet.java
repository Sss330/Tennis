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
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("pages/match-score.jsp").forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //todo достать айдишники того кто выйграл
        //todo


        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        MatchScore currentMatch = ongoingMatchesService.getMatch(uuid);


        // todo поменять на норм параметр айдишника

        String servedPlayerIdString = req.getParameter("served-player_id");

        long servedPlayerId = Long.parseLong(servedPlayerIdString);

        //todo вызвается сервисчный метод который обновляет счёт


        //todo поменять параметры
        matchScoreCalculationService.winServe(currentMatch, servedPlayerId);

        //todo вызывается метод проверки закончености матча


        /* if (matchScoreCalculationService.isMatchOver()) {

        }*/

        //todo если матч не закончился рендириться

        //todo если матч закончился удаляю из мапы и записываю в табличку  рендер финального счёта

        //todo если матч закончился то


    }
}
