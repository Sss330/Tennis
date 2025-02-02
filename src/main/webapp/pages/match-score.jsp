<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.comon.MatchScore" %>
<%@ page import="model.comon.Score" %>
<%@ page import="model.entity.Player" %>
<%
    // Из request достаём наши объекты
    MatchScore currentMatch = (MatchScore) request.getAttribute("match");
    String uuid = (String) request.getAttribute("uuid");

    // Если вы заранее достали имена через сервис/DAO и положили в request
    // допустим, firstPlayerName и secondPlayerName:
    String firstPlayerName = (String) request.getAttribute("firstPlayerName");
    String secondPlayerName = (String) request.getAttribute("secondPlayerName");

    // А если пока имена не положили в request, можно сделать временные заглушки:
    // (Заглушка лишь для примера, в реальности лучше достать из DAO в doGet.)
    if (firstPlayerName == null) {
        firstPlayerName = "Player 1 (ID=" + currentMatch.getFirstPlayerId() + ")";
    }
    if (secondPlayerName == null) {
        secondPlayerName = "Player 2 (ID=" + currentMatch.getSecondPlayerId() + ")";
    }

    // Данные о счёте
    Score scoreFirstPlayer = currentMatch.getScoreFirstPlayer();
    Score scoreSecondPlayer = currentMatch.getScoreSecondPlayer();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <script src="<%= request.getContextPath() %>/js/app.js"></script>
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="<%= request.getContextPath() %>/images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="<%= request.getContextPath() %>/">Home</a>
                <a class="nav-link" href="<%= request.getContextPath() %>/pages/matches.jsp">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                    <th class="table-text"></th> <!-- для кнопки -->
                </tr>
                </thead>
                <tbody>
                <!-- Первая строка - первый игрок -->
                <tr class="player1">
                    <td class="table-text"><%= firstPlayerName %></td>
                    <td class="table-text"><%= scoreFirstPlayer.getSets() %></td>
                    <td class="table-text"><%= scoreFirstPlayer.getGames() %></td>
                    <td class="table-text">
                        <%= scoreFirstPlayer.isAdvantage() ? "AD" : scoreFirstPlayer.getPoints() %>
                    </td>
                    <td class="table-text">
                        <form action="<%= request.getContextPath() %>/match-score" method="post">
                            <!-- Передаём uuid матча -->
                            <input type="hidden" name="uuid" value="<%= uuid %>"/>
                            <!-- ID первого игрока, которому начисляем очко -->
                            <input type="hidden" name="served-player_id"
                                   value="<%= currentMatch.getFirstPlayerId() %>"/>
                            <button type="submit" class="score-btn">Score</button>
                        </form>
                    </td>
                </tr>
                <!-- Вторая строка - второй игрок -->
                <tr class="player2">
                    <td class="table-text"><%= secondPlayerName %></td>
                    <td class="table-text"><%= scoreSecondPlayer.getSets() %></td>
                    <td class="table-text"><%= scoreSecondPlayer.getGames() %></td>
                    <td class="table-text">
                        <%= scoreSecondPlayer.isAdvantage() ? "AD" : scoreSecondPlayer.getPoints() %>
                    </td>
                    <td class="table-text">
                        <form action="<%= request.getContextPath() %>/match-score" method="post">
                            <!-- Передаём uuid матча -->
                            <input type="hidden" name="uuid" value="<%= uuid %>"/>
                            <!-- ID второго игрока, которому начисляем очко -->
                            <input type="hidden" name="served-player_id"
                                   value="<%= currentMatch.getSecondPlayerId() %>"/>
                            <button type="submit" class="score-btn">Score</button>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from
            <a href="https://zhukovsd.github.io/java-backend-learning-course/">
                zhukovsd/java-backend-learning-course
            </a> roadmap.
        </p>
    </div>
</footer>
</body>
</html>
