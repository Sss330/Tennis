<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Tennis Scoreboard | Finished Matches</title>

  <!-- Google Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">

  <!-- Подключаем CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

  <!-- JS-файл -->
  <script src="${pageContext.request.contextPath}/js/app.js"></script>
</head>
<body>
<header class="header">
  <section class="nav-header">
    <div class="brand">
      <div class="nav-toggle">
        <img src="${pageContext.request.contextPath}/images/menu.png" alt="Logo" class="logo">
      </div>
      <span class="logo-text">TennisScoreboard</span>
    </div>
    <div>
      <nav class="nav-links">
        <a class="nav-link" href="${pageContext.request.contextPath}/main-page">Home</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
      </nav>
    </div>
  </section>
</header>

<main>
  <div class="container">
    <h1>Matches</h1>

    <!-- Блок для фильтрации по имени -->
    <div class="input-container">
      <!-- Используем flex для выравнивания поля ввода и кнопки -->
      <form method="get" action="${pageContext.request.contextPath}/matches" style="display: flex; gap: 10px;">
        <input
                class="input-filter"
                placeholder="Filter by name"
                type="text"
                name="filter_by_player_name"
                value="${filter_by_player_name}"
                style="flex: 1;"
        />
        <a href="${pageContext.request.contextPath}/matches" style="flex-shrink: 0;">
          <button type="button" class="btn-filter">Reset Filter</button>
        </a>
      </form>
    </div>

    <!-- Таблица с матчами -->
    <table class="table-matches">
      <thead>
      <tr>
        <th>Player One</th>
        <th>Player Two</th>
        <th>Winner</th>
      </tr>
      </thead>
      <tbody>
      <c:if test="${empty matches}">
        <tr>
          <td colspan="3">No matches found.</td>
        </tr>
      </c:if>

      <c:forEach var="match" items="${matches}">
        <tr>
          <td>${match.player1.name}</td>
          <td>${match.player2.name}</td>
          <td><span class="winner-name-td">${match.winner.name}</span></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

    <!-- Пагинация -->
    <div class="pagination">
      <c:if test="${currentPage > 1}">
        <a class="prev" href="?page=${currentPage - 1}&filter_by_player_name=${filter_by_player_name}">&lt;</a>
      </c:if>

      <c:forEach var="i" begin="1" end="${totalPages}">
        <a class="num-page ${i == currentPage ? 'current' : ''}"
           href="?page=${i}&filter_by_player_name=${filter_by_player_name}">
            ${i}
        </a>
      </c:forEach>

      <c:if test="${currentPage < totalPages}">
        <a class="next" href="?page=${currentPage + 1}&filter_by_player_name=${filter_by_player_name}">&gt;</a>
      </c:if>
    </div>
  </div>
</main>

<footer>
  <div class="footer">
    <p>
      &copy; Tennis Scoreboard, project from
      <a href="https://zhukovsd.github.io/java-backend-learning-course/" target="_blank">
        zhukovsd/java-backend-learning-course
      </a>
      roadmap.
    </p>
  </div>
</footer>
</body>
</html>
