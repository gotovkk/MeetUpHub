<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Event Manager</title>
    <!-- Подключаем CSS с правильным контекстом -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<h2>Менеджер мероприятий</h2>

<div class="button-container">
    <button onclick="window.location.href='<%= request.getContextPath() %>/event'">Мероприятия</button>
    <button onclick="window.location.href='<%= request.getContextPath() %>/user'">Пользователи</button>
    <button onclick="window.location.href='<%= request.getContextPath() %>/login'">Вход</button>
    <button onclick="window.location.href='<%= request.getContextPath() %>/register'">Регистрация</button>
</div>

</body>
</html>
