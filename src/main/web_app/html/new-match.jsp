
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | New Match</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <nav class="nav-links">
            <a class="nav-link" href="index.jsp">Home</a>
            <a class="nav-link" href="finished_matches.jsp">Matches</a>
        </nav>
    </section>
</header>
<main>
    <div class="container">
        <h1>Start New Match</h1>
        <div class="form-container center">
            <form method="post" action="new-match">
                <!-- Отображение ошибки -->
                <c:if test="${not empty errorMessage}">
                    <p style="color: #ff0000;">${errorMessage}</p>
                </c:if>
                <label class="label-player" for="playerOne">Player One</label>
                <input class="input-player" placeholder="Name" type="text" id="playerOne"
                       name="playerOne" pattern="[A-Za-z]\\.[A-Za-z]+" required
                       title="Enter a name in the format n. surname ">
                <label class="label-player" for="playerTwo">Player Two</label>
                <input class="input-player" placeholder="Name" type="text" id="playerTwo"
                       name="playerTwo" pattern="[A-Za-z]\\.[A-Za-z]+" required
                       title="Enter a name in the format n. surname ">
                <input class="form-button" type="submit" value="Start">
            </form>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>.</p>
    </div>
</footer>
</body>
</html>
