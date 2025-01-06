<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Home</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <script src="js/app.js"></script>
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
        <h1>Welcome to Tennis Scoreboard</h1>
        <p>Manage your tennis matches, record results, and track rankings</p>
        <div class="welcome-image"></div>
        <div class="form-container center">
            <a class="homepage-action-button" href="#">
                <form method="post" action="<%= request.getContextPath() %>/main-page">
                    <button class="btn start-match" type="submit">
                        Start a new match
                    </button>
                </form>
            </a>
            <a class="homepage-action-button" href="<%= request.getContextPath() %>/matches">
                <button class="btn view-results" type="button">
                    View match results
                </button>
            </a>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from
            <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.
        </p>
    </div>
</footer>
</body>
</html>
