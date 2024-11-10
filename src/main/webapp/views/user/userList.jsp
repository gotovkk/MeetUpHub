<%@ page import="java.util.List" %>
<%@ page import="meetuphub.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User list</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/user.css">
</head>
<body>
<div class="container">
    <form action="<%= request.getContextPath() %>/register" method="post">
        <button type="submit">Добавить пользователя</button>
    </form>

    <h2>Список пользователей</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>Логин</th>
            <th>Почта</th>
            <th>Создан</th>
            <th>Действия</th>
        </tr>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
        %>
        <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getName() %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getCreatedAt() %></td>
            <td>
                <a href="views/user/updateUser.jsp?id=<%= user.getId() %>" class="btn-action">Обновить</a> |
                <a href="views/user/deleteUser.jsp?id=<%= user.getId() %>" class="btn-action delete">Удалить</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5">Пользователи не найдены.</td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
