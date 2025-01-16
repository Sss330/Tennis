<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tennis Scoreboard | New Match</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">
                <a href="<%= request.getContextPath() %>/main-page" style="text-decoration: none; color: inherit;">
                    TennisScoreboard
                </a>
            </span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="<%= request.getContextPath() %>/main-page">Home</a>
                <a class="nav-link" href="<%= request.getContextPath() %>/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Start new match</h1>
        <div class="new-match-image"></div>
        <div class="form-container center">
            <form method="post" action="<%= request.getContextPath() %>/new-match">
                <p style="color: red;"><%= request.getAttribute("error") %></p>
                <label class="label-player" for="playerOne">Player one</label>
                <input class="input-player" id="playerOne" name="playerOne" placeholder="Name" type="text" required>

                <label class="label-player" for="playerTwo">Player two</label>
                <input class="input-player" id="playerTwo" name="playerTwo" placeholder="Name" type="text" required>

                <input class="form-button" type="submit" value="Start">
            </form>
        </div>
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
