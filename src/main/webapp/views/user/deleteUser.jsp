<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Delete User</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/deleteUser.css">
</head>
<body>
<div class="container">
    <h2>Удаление пользователя</h2>
    <p>Вы уверены, что хотите удалить этого пользователя?</p>
    <form action="<%= request.getContextPath() %>/user" method="post">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
        <button type="submit">Да</button>
        <button type="button" class="cancel" onclick="window.history.back()">Отмена</button>
    </form>
</div>
</body>
</html>
